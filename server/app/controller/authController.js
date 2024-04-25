const userModel = require('../model/user');

const registerUser = (req, res) => {
    const user = req.body;
    userModel.createUser(user, (err) => {
        if (err) {
            res.status(500).json({ error: err.message });
            return;
        }
        res.json({ message: 'User registered successfully' });
    });
};

const loginUser = (req, res) => {
    const { username, password } = req.body;
    userModel.loginUser(username, password, (err, user) => {
        if (err) {
            res.status(500).json({ error: err.message });
            return;
        }
        if (!user) {
            res.status(401).json({ message: 'Invalid credentials' });
            return;
        }
        res.json({ message: 'Login successful', role: user.role });
    });
};


module.exports = {
    registerUser,
    loginUser,
};
