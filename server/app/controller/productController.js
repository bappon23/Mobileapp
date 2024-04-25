const productModel = require('../model/product');

const createProduct = async (product) => {
    try {
        // Assuming createProduct in productModel accepts a product object
        const productId = await productModel.createProduct(product);
        console.log(`Product created successfully: ${productId}`);
        return productId;
    } catch (error) {
        console.error('Failed to create product:', error);
        throw error;
    }
};

const getProductById = async (req, res) => {
    try {
        const productId = req.params.id;
        const product = await productModel.getProductById(productId);
        if (!product) {
            res.status(404).json({ message: 'Product not found' });
            return;
        }
        res.json(product);
    } catch (error) {
        res.status(500).json({ message: 'Failed to get product', error: error.message });
    }
};

const updateProduct = async (req, res) => {
    try {
        const productId = req.params.id;
        const updatedProduct = req.body;
        const result = await productModel.updateProduct(productId, updatedProduct);
        if (result === 0) {
            res.status(404).json({ message: 'Product not found' });
            return;
        }
        res.json({ message: 'Product updated successfully' });
    } catch (error) {
        res.status(500).json({ message: 'Failed to update product', error: error.message });
    }
};

const deleteProductById = async (req, res) => {
    try {
        const productId = req.params.id;
        const result = await productModel.deleteProductById(productId);
        if (result === 0) {
            res.status(404).json({ message: 'Product not found' });
            return;
        }
        res.json({ message: 'Product deleted successfully' });
    } catch (error) {
        res.status(500).json({ message: 'Failed to delete product', error: error.message });
    }
};

const getAllProducts = async (req, res) => {
    try {
        const products = await productModel.getAllProducts();
        res.json(products);
    } catch (error) {
        res.status(500).json({ message: 'Failed to get products', error: error.message });
    }
};
const searchProductByName = async (req, res) => {
    try {
        const productName = req.query.q;
        const products = await productModel.searchProductsByName(productName); 
        if (products.length === 0) {
            res.status(404).json({ message: 'Product not found' });
            return;
        }
        res.json(products); 
    } catch (error) {
        res.status(500).json({ message: 'Failed to search products by name', error: error.message });
    }
};


module.exports = {
    createProduct,
    getProductById,
    updateProduct,
    deleteProductById,
    getAllProducts,
    searchProductByName 
};
