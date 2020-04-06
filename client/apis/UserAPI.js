var userSchema = require('../schemas/User.js');
var password = require('../tools/password.js');
var fs = require('fs');
var sharp = require('sharp');

class UserApi {
    constructor(app) {
        this.app = app;
        this.bindRoutes(app)
    }

    bindRoutes(app) {
        this.bindCreateRoute(app);
        this.bindGetAllRoute(app);
        this.bindGetFindUserRoute(app);
        this.bindLoginRoute(app);
        this.bindUpdateRoute(app);
    }

    bindCreateRoute(app) {
        // route for creating a new user
        app.use('/users/create', (req, res) => {
            // construct the user from the form data which is in the request body
            var encode = password.hashAndSalt(req.body.password);
            var newUser = new userSchema ({
                username: req.body.username,
                email: req.body.email,
                salt: encode.salt,
                hash: encode.hash,
                photo: req.body.photo ? req.body.photo : null,
                bio: req.body.bio ? req.body.bio : '',
                rating: 0,
                points: 0
            });

            // save the user to the database
            newUser.save( (err) => { 
                if (err) {
                    console.log(err);
                    res.send({success: false,
                              code: err.code});
                } else {
                    res.send({success: true,
                              user: newUser});
                    // display the 'successfully created' page using EJS
                    // res.render('user_created', {user : newUser});
                }
            } ); 
        });
    }

    bindUpdateRoute(app) {
        // route for creating a new user
        app.use('/users/update', (req, res) => {
            if (req.body.id) {
                console.log(req.body.id);
                userSchema.findById( (req.body.id), (err, user) => {
                    if (err) {
                        console.log('Error: ' + err);
                    } else if (user) {
                        this.update(user, req, res);
                    }
                });
            } else {
                var queryObject = [{email : req.body.email ? req.body.email : null},
                               {username: req.body.username ? req.body.username : null}];

                console.log(queryObject);
                userSchema.findOne( {$or:queryObject}, (err, user) => {
                    if (err) {
                        //res.type('html').status(200);
                        console.log('Error: ' + err);
                        res.send({user: null, err: err});
                    } else {
                        if (user) {
                            console.log("found user " + user);
                            //res.render('user_findone', {user: user});
                            this.update(user, req, res);
                        }
                    }
                });
            }
        });
    }

    // helper to update document
    async update(user, req, res) {
        // update user data
        var promise = this.resize(req.body.photo);
        promise.then(data => {
            user.bio = req.body.bio;
            user.photo = Buffer.from(data).toString('base64');
            this.saveUser(user, res);
        });
        
    }

    // resize image to 250x250 if larger
    resize(img) {
        var buffer = Buffer.from(img, 'base64');
        return sharp(buffer).resize({width: 250, height: 250}).toBuffer();
    }

    saveUser(user, res) {
        user.save((err) => {
            if (err) {
                console.log("Error: " + err);
            } else {
                console.log("Saved: " + user);
                res.send({user: user});
            }

        });
    }


    bindGetAllRoute(app) {
        // route for showing all the users
        app.use('/users/all', (req, res) => {
            // find all the user objects in the database
            userSchema.find( {}, (err, users) => {
                if (err) {
                    res.type('html').status(200);
                    res.write('Error: ' + err);
                    console.log(err);
                    res.end();
                } else {
                    if (users.length == 0) {
                        res.type('html').status(200);
                        res.write('There are no users');
                        res.end();
                        return;
                    }
                    res.send(users);
                    // use EJS to show all the users
                    // res.render('user_all', { users: users });
                }
            }).sort({ 'age': 'asc' }); // this sorts them BEFORE rendering the results
        });
    }

    bindLoginRoute(app) {
        //route for login
        app.use('/users/login', (req, res) => {
            console.log(req.body);
            userSchema.findOne( {$or:[{ username: req.body.login },
                               { email: req.body.login }]}, (err, user) => {
                console.log(req.body.login);
                console.log(req.body.password)
                if (err) {
                    res.type('html').status(200);
                    console.log('Error: ' + err);
                    res.write(err);
                } else {
                    if (user) {
                        var salt = user.salt;
                        var hash = user.hash;
                        if (password.authenticate(req.body.password, salt, hash)) {
                            res.send({ auth: true,
                                       user: user });
                        } else {
                            res.send({ auth: false});
                        }
                    } else {
                        res.type('html').status(200);
                        console.log('No such user');
                        res.send({ auth: false});
                    }
                }
            });
        });
    }

    bindGetFindUserRoute(app) {
        app.use('/users/find', (req, res) => {
            if (req.body.id) {
                userSchema.findById( (req.body.id), (err, user) => {
                    if (err) {
                        console.log('Error: ' + err);
                        res.send(err);
                    } else {
                        if (user) {
                            res.send(user);
                        } else {
                            res.send({err: "there is no such user"});
                        }
                    }
                });
            } else {
                var queryObject = [{email : req.body.email ? req.body.email : null},
                               {username: req.body.username ? req.body.username : null}];
                userSchema.findOne( {$or:queryObject}, (err, user) => {
                    if (err) {
                        //res.type('html').status(200);
                        console.log('Error: ' + err);
                        res.send(err);
                    } else {
                        if (user) {
                            //res.render('user_findone', {user: user});
                            res.send(user);
                            return;
                        } else {
                            //res.type('html').status(200);
                            //res.write('There is no user with that : ' + queryObject);
                            //res.end();
                            res.send({err: 'no such users exist'});
                            return;
                        }
                    }
                });
            }
        });
    }
}

module.exports = UserApi;
