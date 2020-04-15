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
        this.bindAcceptFavorRoute(app);
        this.bindFindFavorsSubmittedRoute(app);
        this.bindFindFavorsAcceptedRoute(app);
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
                    console.log(err);
                    res.send({err: err});
                }
                else {
                    // display the "successfully created" page using EJS
                    //res.render('favor_created', {favor : newFavor});
                    res.send(newFavor);
                }
            } ); 
        });
    }

    bindDeleteRoute(app) {
        // route for deleting a favor
        app.use('/favors/delete', (req, res) => {
            favorId = mongoose.Types.ObjectId(req.body.id);
            // try to find one favor in the database
            favorSchema.findById(favorId, (err1,favor) => {
                if (err1) {
                    console.log('Error: ' + err);
                    res.send({err: err});
                }
                else {
                    if (favor) {
                        favorSchema.deleteOne({_id: favorId}, (err2) => {
                            if (err2) {
                                console.log('Error: ' + err);
                                res.send({err: err});
                            } else {
                                res.send(favor);
                            }
                        });
                    }
                    else {
                        console.log('There is no favor with that id');
                        res.send({err: "there is no favor with that id"});
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
                    res.json([]);
                }
                else if (favors.length == 0) {
                    res.json([]);
                } else if (favors.length == 1) {
                    var favor = favors[0];
                    res.send([favor]); 
                } else {
                    // construct an array out of the result
                    var returnArray = [];
                    favors.forEach( (favor) => {
                    returnArray.push( favor );
                    });
                    res.json(favors); 
                }
            });
        });
    }


    // endpoint accepts json request with fields favorId and userId,
    // assigns user of userId to accept favor of favorId
    bindAcceptFavorRoute(app) {
        app.use("/favors/accept", (req, res) => {
            var favorId = mongoose.Types.ObjectId(req.body.favorId);
            var userId = mongoose.Types.ObjectId(req.body.userId);
            favorSchema.findById(favorId, (err1, favor) => {
                if (err1) {
                    console.log('Error: ', err);
                    res.send({err: err});
                }
                else {
                    if (favor) {
                        favor.acceptedBy = userId;
                        favor.save((err) => {
                            if (err) {
                                console.log('Error: ', err);
                            } else {
                                res.send(favor);
                            }
                        })
                    }
                    else {
                        console.log('There is no favor with that id');
                        res.send({err: "there is no favor with that id"});
                    }
                }
            });
        });
    }


    // get all favors submitted by a given user from userId
    bindFindFavorsSubmittedRoute(app) {
        app.use("/favors/getallfromuser", (req, res) => {
            var userId = mongoose.Types.ObjectId(req.body.userId);
            favorSchema.find( {userId: userId}, (err1, favors) => {
                if (err1) {
                    console.log('Error: ' + err);
                    res.send({err: err});
                }
                else {
                    if (favors) {
                        res.send(favors);
                    }
                    else {
                        console.log('There are no favors by that user');
                        res.send({err: "there are no favors by that user"});
                    }
                }
            });
        });
    }

    // get all favors undertaken by a given user from userId
    bindFindFavorsAcceptedRoute(app) {
        app.use("/favors/getallacceptedbyuser", (req, res) => {
            var userId = mongoose.Types.ObjectId(req.body.userId);
            favorSchema.find( {acceptedBy: userId}, (err1, favors) => {
                if (err1) {
                    console.log('Error: ' + err);
                    res.send({err: err});
                }
                else {
                    if (favors) {
                        res.send(favors);
                    }
                    else {
                        console.log('There are no favors undertaken that user');
                        res.send({err: "there are no favors undertaken that user"});
                    }
                }
            });
        });
    }
}

module.exports = FavorApi;