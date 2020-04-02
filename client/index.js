// set up Express
var express = require('express');
var app = express();

// set up EJS
app.set('view engine', 'ejs');

// set up BodyParser
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));

/***************************************/

// load APIs
var userApi = require('./apis/UserAPI.js');
// hook express app to API
new userApi(app)

var favorApi = require('./apis/FavorAPI.js');
new favorApi(app)

/*************************************************/

app.use('/public', express.static('public'));

app.use('/', (req, res) => { res.redirect('/public/public.html'); } );

app.listen(3000,  () => {
    console.log('Listening on port 3000');
    });
