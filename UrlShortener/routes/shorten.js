const express = require('express');
const dotenv = require('dotenv');
const validUrl = require('valid-url');
const shortid = require('shortid');
const Url = require('../models/Url');
const router = express.Router();

dotenv.config({ path: '../config/config.env' });
const baseUrl = `https://${process.env.HOST}`;

// @desc        Get all URL records
// @route       GET /api/url
// @access      Public
router.get('/', async (req, res) => {
    try {
        const urls = await Url.find();
        return res.status(200).json({
            count: urls.length,
            data: urls,
        });
    } catch (err) {
        res.status(500).json('Server error');
    }
});

// @desc        Create short URL
// @route       POST /api/url/shorten
// @access      Public
router.post('/shorten', async (req, res) => {
    const { longUrl, customUrlId } = req.body;

    // Check base URL
    if (!validUrl.isUri(baseUrl)) {
        return res.status(401).json('Invalid base URL');
    }

    // Check long URL
    if (validUrl.isUri(longUrl)) {
        try {
            let url = await Url.findOne({ longUrl });
            const brandedUrlId = await Url.findOne({ urlId: customUrlId });

            if (brandedUrlId) {
                return res
                    .status(401)
                    .json(
                        'This URL ending already exists, please change another'
                    );
            }

            if (url) {
                res.status(202).json({ shortUrl: url.shortUrl });
            } else {
                // Create short URL
                let urlId = '';
                if (customUrlId.trim() === '') {
                    // Enforce unique URL ID
                    urlId = shortid.generate();
                    while (await Url.findOne({ urlId })) {
                        urlId = shortid.generate();
                    }
                } else {
                    urlId = customUrlId;
                }
                const shortUrl = baseUrl + '/url/' + urlId;

                // Create new URL record
                url = new Url({
                    longUrl,
                    shortUrl,
                    urlId,
                    createdDate: new Date(),
                    lastUpdatedDate: new Date(),
                });
                await url.save();
                res.status(200).json({ shortUrl: url.shortUrl });
            }
        } catch (err) {
            res.status(500).json('Server error');
        }
    } else {
        res.status(401).json('Please provide a valid URL');
    }
});

// @desc        Update long URL
// @route       PUT /api/url/shorten
// @access      Public
router.put('/shorten/:urlid', async (req, res) => {
    const urlId = req.params.urlid;
    const { longUrl } = req.body;

    // Check base URL
    if (!validUrl.isUri(baseUrl)) {
        return res.status(401).json('Invalid base URL');
    }

    // Check new URL
    if (validUrl.isUri(longUrl)) {
        try {
            let url = await Url.findOne({ longUrl });
            let urlIdDb = await Url.findOne({ urlId });

            if (!urlIdDb) {
                return res.status(401).json('This URL ID does not exist');
            }
            if (url) {
                return res
                    .status(401)
                    .json('This URL already exists, please change another');
            } else {
                await Url.updateOne(
                    { urlId },
                    { longUrl, lastUpdatedDate: new Date() }
                );
                res.status(200).json('Success');
            }
        } catch (err) {
            res.status(500).json('Server error');
        }
    } else {
        res.status(401).json('Please provide a valid URL');
    }
});

// @desc        Dalete URL record by URL ID
// @route       DELETE /api/url/shorten/:urlid
// @access      Public
router.delete('/shorten/:urlid', async (req, res) => {
    const urlId = req.params.urlid;

    // Check base URL
    if (!validUrl.isUri(baseUrl)) {
        return res.status(401).json('Invalid base URL');
    }

    // Check new URL
    try {
        let url = await Url.findOne({ urlId });

        if (!url) {
            return res
                .status(404)
                .json('This URL ID does not exist, please return to home page');
        } else {
            await Url.deleteOne({ urlId });
            res.status(200).json('Success');
        }
    } catch (err) {
        res.status(500).json('Server error');
    }
});

module.exports = router;
