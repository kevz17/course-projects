const express = require('express');
const dotenv = require('dotenv');
const router = express.Router();
const Url = require('../models/Url');
dotenv.config({ path: '../config/config.env' });

// @desc        Redirect to long URL
// @route       GET /url/:urlId
// @access      Public
router.get('/:urlid', async (req, res) => {
    try {
        const url = await Url.findOne({ urlId: req.params.urlid });

        if (url) {
            return res.redirect(url.longUrl);
        } else {
            return res
                .status(404)
                .json(
                    'Invalid URL ID, please check and provide URL with a valid URL ID'
                );
        }
    } catch (err) {
        res.status(500).json('Server error');
    }
});

// @desc        Redirect to URL editting page
// @route       GET /url/:urlId/edit
// @access      Public
router.get('/:urlid/edit', async (req, res) => {
    try {
        const urlId = req.params.urlid;
        const url = await Url.findOne({ urlId: urlId });

        if (url) {
            return res.redirect(
                `https://${process.env.HOST}/edit?urlid=${urlId}`
            );
        } else {
            return res
                .status(404)
                .json(
                    'Invalid URL ID, please check and provide URL with a valid URL ID'
                );
        }
    } catch (err) {
        res.status(500).json('Server error');
    }
});

module.exports = router;
