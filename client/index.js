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
new userApi(app)

var favorApi = require('./apis/FavorAPI.js');
new favorApi(app)

var chartApi = require('./apis/chartAPI.js');
new chartApi(app)

/*************************************************/

app.use('/Login', express.static('Login'));

app.use('/', (req, res) => { res.redirect('/Login/login.html'); } );

app.listen(3000,  () => {
    console.log('Listening on port 3000');
    });
