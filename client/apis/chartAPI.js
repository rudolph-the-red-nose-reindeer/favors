// logs message upon starting app
console.log('app started');

// imports express module & initializes express app

// imports built-in node path module
const path = require('path');

// imports body parser module
const bodyParser = require('body-parser');

// sets port varaible
const port = process.env.PORT || 3000;

// imports mongodb node driver & creates const for hosted mongo url
const MongoClient = require('mongodb').MongoClient;

const dbName = 'main';
// hosted mongodb instance url
const url = 'mongodb+srv://admin:penguin@cis350-7-hquxc.mongodb.net/main?retryWrites=true&w=majority';
class chartAPI {
	constructor(app) {
		this.app = app;
		this.bindRoutes(app);
	}

// creates GET request route for index
	

	bindRoutes(app) {
	this.bindConnectRoute(app);
	this.bindDataRoute(app);
	this.bindData1Route(app);
	}

bindConnectRoute(app) {
app.get('/getdata', (req, res) => {

  // opens connection to mongodb
  MongoClient.connect(url, {
useUnifiedTopology: true}).then(client => {

    // creates const for our database
    const db = client.db(dbName);

    // creates const for 'employees' collection in database
    const col = db.collection('favors');

    // finds ALL employees in 'employees' collection/converts to array
    col.find({}).toArray().then(docs => {

      // logs message upon finding collection
      console.log('found employees for indexyay');


      // renders index ejs template and passes favors array as data
      res.render('index', {
        favors: docs

      });

      // closes connection to mongodb and logs message
      client.close(() => console.log('connection closed'));

    // checks for error in finding 'employees' collection
    }).catch(err => {

      // logs message upon error in finding 'employees' collection
      console.log('error finding employees', err);

    });

  // checks for error in connecting to mongodb
  }).catch(err => {

    // logs message upon error connecting to mongodb
    console.log('error connecting to mongodb', err);

  });

});
}

bindDataRoute(app) {
// creates GET request route for /api/data page
app.get('/api/data', (req, res) => {

  // opens connection to mongodb
  MongoClient.connect(url).then(client => {

    // creates const for our database
    const db = client.db(dbName);

    // creates const for 'employees' collection in database
    const col = db.collection('favors');

    // finds ALL employees in 'employees' collection/converts to array
    col.find({}).toArray().then(docs => {

      // logs message upon finding 'employees' collection
      console.log('found favors for api');

      // sends/renders employees array to /api/data page
      res.send(docs);

      // closes connection to mongodb and logs message
      client.close(() => console.log('connection closed'));

    // checks for error finding 'employees' collection
    }).catch(err => {

      // logs message upon error finding 'employees' collection
      console.log('unable to find favors for api', err);

    });

  // checks for error in connecting to mongodb
  }).catch(err => {

    // logs message upon error connecting to mongodb
    console.log('error connecting to mongodb', err);

  });

});
}

bindData1Route(app) {

// creates GET request route for /api/data page
app.get('/api/data1', (req, res) => {

  // opens connection to mongodb
  MongoClient.connect(url).then(client => {

    // creates const for our database
    const db = client.db(dbName);

    // creates const for 'employees' collection in database
    const col = db.collection('users');

    // finds ALL employees in 'employees' collection/converts to array
    col.find({}).toArray().then(docs => {

      // logs message upon finding 'employees' collection
      console.log('found favors for api');

      // sends/renders employees array to /api/data page
      res.send(docs);

      // closes connection to mongodb and logs message
      client.close(() => console.log('connection closed'));

    // checks for error finding 'employees' collection
    }).catch(err => {

      // logs message upon error finding 'employees' collection
      console.log('unable to find favors for api', err);

    });

  // checks for error in connecting to mongodb
  }).catch(err => {

    // logs message upon error connecting to mongodb
    console.log('error connecting to mongodb', err);

  });

});
	}
}
// listens to port 300 and logs message when listening
module.exports = chartAPI;
