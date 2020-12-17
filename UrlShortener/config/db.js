const mongoose = require('mongoose');

const mongoDBEndpoint =
    process.env.MONGODB_URI || 'mongodb://127.0.0.1/Cluster0';

const connectDB = async () => {
    try {
        await mongoose.connect(mongoDBEndpoint, {
            useNewUrlParser: true,
            useUnifiedTopology: true,
        });
        console.log('MongoDB Connected...');
    } catch (err) {
        console.error(err.message);
        process.exit(1);
    }
};

module.exports = connectDB;
