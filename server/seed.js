// Import necessary modules
const productController = require('./app/controller/productController');
const products = [
    {
        name: 'Acoustic Takamine GN71CE-BSB',
        description: 'Description for Product 1',
        image: 'https://pianohouse.vn/image/cache/96x72/catalog/products/Guitar%20/Guitar%20Takamine/Acoustic%20Takamine/Acoustic%20Takamine%20GN71CE-BSB/8.jpg',
        price: 19.99,
        type: 1,
    },
    {
        name: 'Amplifier Guitar Điện Nux Mighty Lite BT MK2',
        description: 'Nux Mighty Lite BT là một sản phẩm tiêu chuẩn mới cho dòng Amp nhỏ tập luyện. Là một loại Amplifier di động nhỏ, nhẹ và có mức độ âm thanh vừa đủ nghe trong phòng riêng hay tại các phòng tập Band.',
        image: 'https://nhaccutienmanh.vn/wp-content/uploads/2022/01/amply-guitar-nux-mighty-lite-bt-nga-3-6-550x550.jpg',
        price: 29.99,
        type: 2
    },
    {
        name: 'Sáo Recorder Yamaha Soprano YRS-23',
        description: 'Description for Product 3',
        image: 'https://nhaccutienmanh.vn/wp-content/uploads/2019/08/sao-recorder-yamaha-soprano-yrs-23-550x550.jpg',
        price: 39.99,
        type: 1
    },
    {
        name: 'ROLAND RD-08',
        description: 'Phím đàn với độ nhạy và độ chính xác cao giúp cải thiện kĩ năng của trẻ. Được trang bị ba bàn đạp để chơi những giai điệu cổ',
        image: 'https://vietthuong.vn/image/cache/catalog/roland/piano/RD-08-400x400.png',
        price: 49.99,
        type: 2
    },
    {
        name: 'Kawai K-300',
        description: 'Đàn piano Kawai K300 là sản phẩm tiêu biểu trong dòng K series của Kawai. Tiếp nối sự thành công của người anh tiền nhiệm là piano Kawai K3, model K3 là cây đàn “Piano Acoustic” đạt danh hiệu cây đàn piano của năm trong suốt 4 năm liền từ 2008 – 2011, do độc giả của tạp chí MMR tại Mỹ bình chọn.',
        image: 'https://vietthuong.vn/image/catalog/kawai/Upright/dan-piano-kawai-k300-mau-den-sang-trong.jpg',
        price: 59.99,
        type: 1
    },
    
];

// Define seeder function
const seedProducts = async () => {
    try {
        for (const product of products) {
            await productController.createProduct(product);
        }
        console.log('Seeder executed successfully');
    } catch (error) {
        console.error('Error seeding products:', error);
    }
};
seedProducts();
