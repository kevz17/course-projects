const express = require('express');
const dotenv = require('dotenv');
const connectDB = require('./config/db');
const shortnerRouter = require('./routes/shorten');
const redirectRouter = require('./routes/redirect');
const cors = require('cors');
const path = require('path');
const PORT = process.env.PORT || 5000;

dotenv.config({ path: './config/config.env' });

const app = express();

// Enable CORS
app.use(cors());

// Connect to database
connectDB();

app.use(express.json({ extended: false }));

// Define Routes
app.use('/api/url', shortnerRouter);
app.use('/url', redirectRouter);
app.use(express.static(path.join(__dirname, 'client', 'build')));

app.get('*', (req, res) => {
    res.sendFile(path.join(__dirname, 'client', 'build', 'index.html'));
});

app.listen(PORT, () =>
    console.log(`Server running in ${process.env.NODE_ENV} on port ${PORT}`)
);
