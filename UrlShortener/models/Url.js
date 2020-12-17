const mongoose = require('mongoose');

const urlSchema = new mongoose.Schema({
    urlId: String,
    longUrl: String,
    shortUrl: String,
    createdDate: { type: String, default: Date.now },
    lastUpdatedDate: { type: String, default: Date.now },
});

module.exports = mongoose.model('Url', urlSchema);
