const express = require('express');
const bodyParser = require('body-parser');
const userController = require('./app/controller/userController')
const authController = require('./app/controller/authController');
const productController= require('./app/controller/productController');
const cartController = require('./app/controller/cartController');
const { initDatabase } = require('./app/db/db');

const app = express();
const port = 3000;

app.use(bodyParser.json());

// Tạo bảng users nếu chưa tồn tại
initDatabase();

// Routes
app.get('/get_user/:username', userController.getUserByUsername);
app.put('/update_password/:username', userController.updateUserPassword);
app.delete('/delete_user/:username', userController.deleteUserByUsername);
app.get('/get_all_users', userController.getAllUsers);
app.post('/register', authController.registerUser);
app.post('/login', authController.loginUser);

app.post('/products', productController.createProduct);
app.get('/products/:id', productController.getProductById);
app.put('/products/:id', productController.updateProduct);
app.delete('/products/:id', productController.deleteProductById);
app.get('/products', productController.getAllProducts);
app.get('/products/search', productController.searchProductByName);

app.post('/cart/add', cartController.addToCart); 
app.get('/cart', cartController.getCartItems); 
app.put('/cart/update/:cartItemId', cartController.updateCartItem); 
app.delete('/cart/remove/:cartItemId', cartController.removeCartItem); 
app.delete('/cart/clear', cartController.clearCart);
app.get('/cart/:username', cartController.getCartItemsByUsername);
app.listen(port, () => {
    console.log(`Server is listening at http://localhost:${port}`);
});
