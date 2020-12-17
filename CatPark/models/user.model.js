const mongoose = require('mongoose');

const UserSchema = new mongoose.Schema(
    {
        userName: String,
        name: String,
        email: String,
        password: String,
        isAdmin: Boolean,
        isSubscribed: { type: Boolean, default: false },
        subscription: { type: Object, default: null },
    },
    {
        timestamps: true,
    }
);

module.exports = mongoose.model('User', UserSchema);
