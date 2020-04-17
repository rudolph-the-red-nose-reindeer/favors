var givenRewardSchema = require('../schemas/GivenReward.js');

class GivenRewardApi {
    constructor(app) {
        this.app = app;
        this.bindRoutes(app)
    }

    bindRoutes(app) {
        this.bindCreateRoute(app);
        this.bindGetAllRoute(app);
    }

    bindCreateRoute(app) {
        // route for creating a new rewards
        // body expects name, description, photo (nullable), points
        app.use('/givenrewards/create', (req, res) => {
            // construct the user from the form data which is in the request body
            var newGivenReward = new givenRewardSchema ({
                userId: req.body.userId,
                rewardId: req.body.rewardId,
                dateRedeemed: Date.parse(req.body.dateRedeemed),
            });

            // save the reward to the database
            newGivenReward.save( (err) => { 
                if (err) {
                    console.log(err);
                    res.send({success: false,
                              code: err.code});
                } else {
                    res.send({success: true,
                              reward: newGivenReward});
                }
            } ); 
        });
    }

    bindGetAllRoute(app) {
        app.use('/givenrewards/all', (req, res) => {
            givenRewardSchema.find({}, (err, givenRewards) => {
                if (err) {
                    console.log("Error: ", err);
                } else {
                    if (givenRewards) {
                        res.send(givenRewards);
                    } else {
                        res.send([]);
                    }
                }
            });
        });
    }
}


module.exports = GivenRewardApi;
