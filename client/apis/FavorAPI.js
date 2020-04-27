var favorSchema = require('../schemas/Favor.js');
var mongoose = require('mongoose');

class FavorApi {
    constructor(app) {
        this.app = app;
        this.bindRoutes(app)
    }

    bindRoutes(app) {
        this.bindCreateRoute(app);
        this.bindDeleteRoute(app);
        this.bindFindAllRoute(app);
    }

    bindCreateRoute(app) {
        // route for creating a new favor
        app.use('/favors/create', (req, res) => {
            // construct the favor from the form data which is in the request body
            var newFavor = new favorSchema ({
                userId: mongoose.Types.ObjectId(req.body.userId),
                datePosted: Date.parse(req.body.datePosted),
                urgency: req.body.urgency,
                location: req.body.location,
                details: req.body.details
            });

            // save the favor to the database
            newFavor.save( (err) => { 
                if (err) {
                    res.type('html').status(200);
                    res.write('Error: ' + err);
                    console.log(err);
                    res.end();
                }
                else {
                    // display the "successfully created" page using EJS
                    res.render('favor_created', {favor : newFavor});
                }
            } ); 
        });
    }

    bindDeleteRoute(app) {
        // route for deleting a favor
        app.use('/favors/delete', (req, res) => {
            // try to find one favor in the database
            favorSchema.findOne( { _id: mongoose.Types.ObjectId(req.query.id) }, (err1, favor) => {
                if (err1) {
                    res.type('html').status(200);
                    console.log('Error: ' + err);
                    res.write(err);
                }
                else {
                    if (favor) {
                        favor.deleteOne( {_id: mongoose.Types.ObjectId(req.query.id)}, (err2, result) => {
                            if (err1) {
                                res.type('html').status(200);
                                console.log('Error: ' + err);
                                res.write(err);
                            } else {
                                res.render('favor_deleted', { favor: favor });
                            }
                        });
                    }
                    else {
                        res.type('html').status(200);
                        console.log('There is no favor with that id');
                        res.write('There is no favor with that id');
                    }
                }
            });
        });
    }

    bindFindAllRoute(app) {
        app.use("/favors/all", (req, res) => {
            favorSchema.find({}, (err, favors) => {
                if (err) {
                    console.log("something horrible happened" + err);
                    res.json({});
                }
                else if (favors.length == 0) {
                    res.json({});
                } else if (favors.length == 1) {
                    var favor = favors[0];
                    var returnArray = [];
                    returnArray.push( { "id" : favor.userId, "date" : favor.datePosted, "location" : favor.location, "urgency" : favor.urgency, 
                                        "details" : favor.details});
                    res.json(returnArray); 
                } else {
                    // construct an array out of the result
                    var returnArray = [];
                    favors.forEach( (favor) => {
                    returnArray.push( { "id" : favor.userId, "date" : favor.datePosted, "location" : favor.location, "urgency" : favor.urgency, 
                                        "details" : favor.details});
                    });
                    res.json(returnArray); 
                }
            });
        });
    }
}

module.exports = FavorApi;
