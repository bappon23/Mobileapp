const sqlite3 = require('sqlite3').verbose();
const db = new sqlite3.Database('database.db');

const createUserTable = () => {
    return new Promise((resolve, reject) => {
        db.run(`CREATE TABLE IF NOT EXISTS users (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            username TEXT,
            password TEXT,
            role TEXT
        )`, (err) => {
            if (err) {
                reject(err);
            } else {
                resolve('User table created successfully');
            }
        });
    });
};

const createProductTable = () => {
    return new Promise((resolve, reject) => {
        db.run(`CREATE TABLE IF NOT EXISTS products (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT,
            description TEXT,
            image TEXT,
            price REAL,
            type TEXT
        )`, (err) => {
            if (err) {
                reject(err);
            } else {
                resolve('Product table created successfully');
            }
        });
    });
};
const createCartTable = () => {
    return new Promise((resolve, reject) => {
        db.run(`CREATE TABLE IF NOT EXISTS cart (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            productId INTEGER,
            quantity INTEGER,
            username TEXT,
            productName TEXT,
            productDescription TEXT, 
            productImage TEXT,  
            productPrice REAL,  
            FOREIGN KEY(productId) REFERENCES products(id)
        )`, (err) => {
            if (err) {
                reject(err);
            } else {
                resolve('Cart table created successfully');
            }
        });
    });
};

const initDatabase = async () => {
    try {
        await createUserTable();
        await createProductTable();
        await createCartTable();
        console.log('Database initialized successfully');
    } catch (error) {
        console.error('Error initializing database:', error);
    }
};

const closeDatabase = () => {
    db.close((err) => {
        if (err) {
            console.error('Error closing database:', err.message);
        } else {
            console.log('Database connection closed.');
        }
    });
};

module.exports = {
    db,
    initDatabase,
    closeDatabase
};
