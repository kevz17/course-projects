const webpush = require('web-push');
const dotenv = require('dotenv');
const User = require('../models/user.model');
const publicVapidKey = process.env.VAPID_KEY_PUBLIC;
const privateVapidKey = process.env.VAPID_KEY_SECRET;

dotenv.config({ path: './config/config.env' });

webpush.setVapidDetails(`${process.env.HOST}`, publicVapidKey, privateVapidKey);

// @desc        Create a subscription
// @route       POST /subscribe
// @access      Private
exports.subscribe = async (req, res) => {
    // Get pushSubscription object
    const { pushTitle, pushContent } = req.body;
    // Create payload
    const payload = JSON.stringify({
        title: pushTitle,
        body: pushContent,
        icon:
            'https://cdn3.iconfinder.com/data/icons/materia-flat-halloween-free/24/039_026_cat_black_witch_halloween-512.png',
    });

    // Get all subscriptions from DB and for each one, push a message
    try {
        const users = await User.find();
        users.map(user => {
            // Pass object into sendNotification
            if (user.subscription.endpoint) {
                webpush
                    .sendNotification(user.subscription, payload)
                    .catch(err => console.log(err));
            }
        });
        return res.status(200).json('Success');
    } catch (err) {
        return res.status(500).json({
            success: false,
            error: 'Server Error',
        });
    }
};
