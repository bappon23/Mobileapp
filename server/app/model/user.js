const { db } = require('../db/db');

const createUser = (user, callback) => {
    const { username, password, role } = user;
    db.run('INSERT INTO users (username, password, role) VALUES (?, ?, ?)', [username, password, role], callback);
};


const loginUser = (username, password, callback) => {
    db.get('SELECT * FROM users WHERE username = ? AND password = ?', [username, password], (err, user) => {
        if (err) {
            callback(err, null);
            return;
        }
        callback(null, user);
    });
};

const getUserByUsername = (username, callback) => {
    db.get('SELECT * FROM users WHERE username = ?', [username], callback);
};

const updateUserPassword = (username, newPassword, callback) => {
    db.run('UPDATE users SET password = ? WHERE username = ?', [newPassword, username], callback);
};

const deleteUserByUsername = (username, callback) => {
    db.run('DELETE FROM users WHERE username = ?', [username], callback);
};
const getAllUsers = (callback) => {
    db.all('SELECT * FROM users', callback);
};
module.exports = {
    createUser,
    getUserByUsername,
    updateUserPassword,
    deleteUserByUsername,
    getAllUsers,
    loginUser
};
