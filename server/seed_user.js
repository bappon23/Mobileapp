const sqlite3 = require('sqlite3').verbose();

// Mở kết nối đến cơ sở dữ liệu
const db = new sqlite3.Database('database.db');

// Tạo bảng users
db.serialize(() => {
    db.run(`CREATE TABLE IF NOT EXISTS users (
        id INTEGER PRIMARY KEY,
        username TEXT NOT NULL UNIQUE,
        password TEXT NOT NULL,
        role TEXT NOT NULL
    )`);

    // Tạo dữ liệu mẫu
    const users = [
        { username: 'admin', password: 'admin123', role: 'admin' },
        { username: 'user1', password: 'password1', role: 'user' },
        { username: 'user2', password: 'password2', role: 'user' }
    ];

    // Thêm dữ liệu mẫu vào bảng users
    const stmt = db.prepare('INSERT INTO users (username, password, role) VALUES (?, ?, ?)');
    users.forEach(user => {
        stmt.run(user.username, user.password, user.role);
    });
    stmt.finalize();

    console.log('Sample data inserted into users table.');

    // Đóng kết nối đến cơ sở dữ liệu
    db.close((err) => {
        if (err) {
            return console.error(err.message);
        }
        console.log('Database connection closed.');
    });
});
