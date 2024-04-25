const userModel = require('../model/user');

const createUser = (req, res) => {
    const user = req.body;
    userModel.createUser(user, (err) => {
        if (err) {
            res.status(500).json({ error: err.message });
            return;
        }
        res.json({ message: 'User created successfully' });
    });
};

const getUserByUsername = (req, res) => {
    const username = req.params.username;
    userModel.getUserByUsername(username, (err, user) => {
        if (err) {
            res.status(500).json({ error: err.message });
            return;
        }
        if (!user) {
            res.status(404).json({ message: 'User not found' });
            return;
        }
        res.json(user);
    });
};

const updateUserPassword = (req, res) => {
    const { username } = req.params;
    const { newPassword } = req.body;
    userModel.updateUserPassword(username, newPassword, (err) => {
        if (err) {
            res.status(500).json({ error: err.message });
            return;
        }
        res.json({ message: 'Password updated successfully' });
    });
};

const deleteUserByUsername = (req, res) => {
    const username = req.params.username;
    userModel.deleteUserByUsername(username, (err) => {
        if (err) {
            res.status(500).json({ error: err.message });
            return;
        }
        res.json({ message: 'User deleted successfully' });
    });
};
const getAllUsers = (req, res) => {
    userModel.getAllUsers((err, users) => {
        if (err) {
            res.status(500).json({ error: err.message });
            return;
        }
        res.json(users);
    });
};
module.exports = {
    createUser,
    getUserByUsername,
    updateUserPassword,
    deleteUserByUsername,
    getAllUsers
};
