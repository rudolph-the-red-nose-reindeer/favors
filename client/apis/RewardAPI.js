var rewardSchema = require('../schemas/Reward.js');

class RewardApi {
    constructor(app) {
        this.app = app;
        this.bindRoutes(app)
    }

    bindRoutes(app) {
        this.bindCreateRoute(app);
        this.bindGetAllRoute(app);
        this.bindGetAllRedeemableRoute(app)
        this.bindUpdateRoute(app);
        this.bindDeleteRoute(app);
    }

    bindCreateRoute(app) {
        // route for creating a new rewards
        // body expects name, description, photo (nullable), points
        app.use('/rewards/create', (req, res) => {
            // construct the user from the form data which is in the request body
            var newReward = new rewardSchema ({
                name: req.body.name,
                description: req.body.description,
                points: req.body.points
            });

            // save the reward to the database
            newReward.save( (err) => { 
                if (err) {
                    console.log(err);
                    res.send({success: false,
                              code: err.code});
                } else {
                    res.send({success: true,
                              reward: newReward});
                }
            } ); 
        });
    }

    bindGetAllRoute(app) {
        app.use('/rewards/all', (req, res) => {
            rewardSchema.find({}, (err, rewards) => {
                if (err) {
                    console.log("Error: ", err);
                } else {
                    if (rewards) {
                        res.send(rewards);
                    } else {
                        res.send([]);
                    }
                }
            });
        });
    }

    bindGetAllRedeemableRoute(app) {
        app.use('/rewards/redeemable', (req, res) => {
            rewardSchema.find({points: {$lte: req.body.points}}, (err, rewards) => {
                if (err) {
                    console.log("Error: ", err);
                } else {
                    if (rewards) {
                        res.send(rewards);
                    } else {
                        res.send([]);
                    }
                }
            });
        });
    }

    bindUpdateRoute(app) {
        // route for updating a reward
        app.use('/rewards/update', (req, res) => {
            var rewardId = req.body.id;
            userSchema.findById(rewardId, (err, reward) => {
                if (err) {
                    console.log('Error: ', err);
                    res.send({err: err});
                } else {
                    if (reward) {
                        reward.name = reward.name ? req.body.name : req.body.name;
                        reward.description = reward.description ? req.body.description : req.body.description;
                        reward.points = reward.points ? req.body.points : req.body.points;

                    }
                }
            });
        });
    }

    bindDeleteRoute(app) {
        // route for deleting a favor
        app.use('/favors/delete', (req, res) => {
            rewardId = mongoose.Types.ObjectId(req.body.id);
            // try to find one favor in the database
            rewardSchema.findById(rewardId, (err1,reward) => {
                if (err1) {
                    console.log('Error: ' + err);
                    res.send({err: err});
                }
                else {
                    if (reward) {
                        rewardSchema.deleteOne({_id: rewardId}, (err2) => {
                            if (err2) {
                                console.log('Error: ' + err);
                                res.send({err: err});
                            } else {
                                res.send(reward);
                            }
                        });
                    }
                    else {
                        console.log('There is no reward with that id');
                        res.send({err: "there is no reward with that id"});
                    }
                }
            });
        });
    }
}


module.exports = RewardApi;
