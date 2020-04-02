var user = require('../schemas/User.js');
var password = require('../tools/password.js');

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
    }

    bindCreateRoute(app) {
        // route for creating a new user
        app.use('/users/create', (req, res) => {
            // construct the user from the form data which is in the request body
            var encode = password.hashAndSalt(req.body.password);
            var newUser = new user ({
                username: req.body.username,
                email: req.body.email,
                salt: encode.salt,
                hash: encode.hash,
                photo: req.body.photo,
                bio: req.body.bio,
                rating: 0,
                points: 0
            });

            // save the user to the database
            newUser.save( (err) => { 
                if (err) {
                    res.type('html').status(200);
                    res.write('Error: ' + err);
                    console.log(err);
                    res.end();
                } else {
                    // display the "successfully created" page using EJS
                    res.render('user_created', {user : newUser});
                }
            } ); 
        });
    }

    bindGetAllRoute(app) {
        // route for showing all the users
        app.use('/users/all', (req, res) => {
            // find all the user objects in the database
            user.find( {}, (err, users) => {
                if (err) {
                    res.type('html').status(200);
                    console.log('Error: ' + err);
                    res.write(err);
                } else {
                    if (users.length == 0) {
                        res.type('html').status(200);
                        res.write('There are no users');
                        res.end();
                        return;
                    }
                    // use EJS to show all the users
                    res.render('user_all', { users: users });
                }
            }).sort({ 'age': 'asc' }); // this sorts them BEFORE rendering the results
        });
    }

    bindLoginRoute(app) {
        //route for login
        app.use('/users/login', (req, res) => {
            user.findOne( {$or:[{ username: req.body.login },
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
                                       id: user._id,
                                       name: user.username });
                        } else {
                            res.send({ auth: false});
                        }
                    } else {
                        res.type('html').status(200);
                        console.log('No such user');
                        res.send('No such user');
                    }
                }
            });
        });
    }

    bindGetFindUserRoute(app) {
        app.use('/users/find', (req, res) => {
            var queryObject = {};
            if (req.body.email) {
                queryObject = {email : req.body.email};
            } else if (req.body.id) {
                queryObject = {_id : req.body.id};
            } else if (req.body.username) {
                queryObject = {username : req.body.username};
            } else {
                res.type('html').status(200);
                console.log('Error: you must specify at least one field');
                return;
            }
            user.findOne(queryObject, (err, user) => {
                if (err) {
                    res.type('html').status(200);
                    console.log('Error: ' + err);
                    res.write(err);
                } else {
                    if (user) {
                        res.render('user_findone', {user: user});
                        return;
                    } else {
                        res.type('html').status(200);
                        res.write('There is no user with that : ' + queryObject);
                        res.end();
                        return;
                    }
                }
            })
        })
    }
}

module.exports = UserApi;
