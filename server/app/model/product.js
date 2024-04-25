const { db } = require('../db/db');

const createProduct = (product) => {
    return new Promise((resolve, reject) => {
        const { name, description, image, price, type } = product;
        db.run('INSERT INTO products (name, description, image, price, type) VALUES (?, ?, ?, ?, ?)', [name, description, image, price, type], function(err) {
            if (err) {
                reject(err);
            } else {
                resolve(this.lastID);
            }
        });
    });
};

const getProductById = (productId) => {
    return new Promise((resolve, reject) => {
        db.get('SELECT * FROM products WHERE id = ?', [productId], (err, product) => {
            if (err) {
                reject(err);
            } else {
                resolve(product);
            }
        });
    });
};

const updateProduct = (productId, updatedProduct) => {
    return new Promise((resolve, reject) => {
        const { name, description, image, price, type } = updatedProduct;
        db.run('UPDATE products SET name = ?, description = ?, image = ?, price = ?, type = ? WHERE id = ?', [name, description, image, price, type, productId], function(err) {
            if (err) {
                reject(err);
            } else {
                resolve(this.changes);
            }
        });
    });
};

const deleteProductById = (productId) => {
    return new Promise((resolve, reject) => {
        db.run('DELETE FROM products WHERE id = ?', [productId], function(err) {
            if (err) {
                reject(err);
            } else {
                resolve(this.changes);
            }
        });
    });
};

const getAllProducts = () => {
    return new Promise((resolve, reject) => {
        db.all('SELECT * FROM products', (err, products) => {
            if (err) {
                reject(err);
            } else {
                resolve(products);
            }
        });
    });
};
const searchProductsByName = (keyword) => {
    return new Promise((resolve, reject) => {
        const query = `SELECT * FROM products WHERE name LIKE '%${keyword}%'`;
        db.all(query, (err, products) => {
            if (err) {
                reject(err);
            } else {
                resolve(products);
            }
        });
    });
};



const searchProductsByType = (type) => {
    return new Promise((resolve, reject) => {
        const query = 'SELECT * FROM products WHERE type = ?';
        db.all(query, [type], (err, products) => {
            if (err) {
                reject(err);
            } else {
                resolve(products);
            }
        });
    });
};

module.exports = {
    createProduct,
    getProductById,
    updateProduct,
    deleteProductById,
    getAllProducts,
    searchProductsByName,
    searchProductsByType
};
