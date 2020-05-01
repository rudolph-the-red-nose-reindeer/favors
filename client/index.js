// set up Express
var express = require('express');
var app = express();

// set up EJS
app.set('view engine', 'ejs');

// set up BodyParser
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json({}));

/***************************************/

// load APIs
var userApi = require('./apis/UserAPI.js');
// hook express app to API
new userApi(app);

var favorApi = require('./apis/FavorAPI.js');
new favorApi(app);

var rewardApi = require('./apis/RewardAPI.js');
new rewardApi(app);

var givenRewardApi = require('./apis/GivenRewardAPI.js');
new givenRewardApi(app);


<<<<<<< Updated upstream
=======
/*************************************************/



app.use('/favors', (req, res) => {
    res.render('favor_all.ejs');
});


announcement = require('./tools/announcement.js');

app.use('/announcement', (req, res) => {
    res.render('announcement.ejs');
});

app.use('/announcementapi/sendtoall', (req, res) => {
    console.log(req.body);
    console.log(req.body.message);
    announcement.sendNotificationToAll(req.body.message, () => {
        console.log('sent to all');
        res.send('success');
    })
});

app.use('/announcementapi/sendtouser', (req, res) => {
    console.log(req.body.username);
    console.log(req.body.message);
    announcement.sendNotificationToUser(req.body.username, req.body.message, () => {
        console.log('sent to ' + req.body.username);
        res.send('success');
    })
});



app.use('/Login', express.static('Login'));
>>>>>>> Stashed changes

/*************************************************/

app.use('/public', express.static('public'));

app.use('/', (req, res) => { res.redirect('/public/public.html'); } );


app.listen(3000,  () => {
    console.log('Listening on port 3000');
    });
