const cartModel = require('../model/cart');

// Thêm sản phẩm vào giỏ hàng

const addToCart = async (req, res) => {
    try {
        const { username, productId, quantity } = req.body;
        
        const existingCartItem = await cartModel.getCartItem(username, productId);
        
        if (existingCartItem) {
            const newQuantity = existingCartItem.quantity + quantity;
            await cartModel.updateCartItem(existingCartItem.id, newQuantity);
            res.json({ message: 'Product quantity updated in cart' });
        } else {
            const cartItemId = await cartModel.addToCart(username, productId, quantity);
            res.json({ message: 'Product added to cart', cartItemId });
        }
    } catch (error) {
        res.status(500).json({ message: 'Failed to add product to cart', error: error.message });
    }
};


// Lấy tất cả các mục trong giỏ hàng
const getCartItems = async (req, res) => {
    try {
        const cartItems = await cartModel.getCartItems();
        res.json(cartItems);
    } catch (error) {
        res.status(500).json({ message: 'Failed to get cart items', error: error.message });
    }
};

// Cập nhật số lượng của một mục trong giỏ hàng
const updateCartItem = async (req, res) => {
    try {
        const cartItemId = req.params.cartItemId;
        const { quantity } = req.body;
        const result = await cartModel.updateCartItem(cartItemId, quantity);
        res.json({ message: result });
    } catch (error) {
        res.status(500).json({ message: 'Failed to update cart item', error: error.message });
    }
};

// Xóa một mục khỏi giỏ hàng
const removeCartItem = async (req, res) => {
    try {
        const cartItemId = req.params.cartItemId;
        const result = await cartModel.removeCartItem(cartItemId);
        res.json({ message: result });
    } catch (error) {
        res.status(500).json({ message: 'Failed to remove cart item', error: error.message });
    }
};

// Xóa toàn bộ các mục trong giỏ hàng
const clearCart = async (req, res) => {
    try {
        const result = await cartModel.clearCart();
        res.json({ message: result });
    } catch (error) {
        res.status(500).json({ message: 'Failed to clear cart', error: error.message });
    }
};
const getCartItemsByUsername = async (req, res) => {
    try {
        const username = req.params.username;
        const cartItems = await cartModel.getCartItemsByUsername(username);
        res.json(cartItems);
    } catch (error) {
        res.status(500).json({ message: 'Failed to get cart items', error: error.message });
    }
};

module.exports = {
    addToCart,
    getCartItems,
    updateCartItem,
    removeCartItem,
    clearCart,
    getCartItemsByUsername
};
