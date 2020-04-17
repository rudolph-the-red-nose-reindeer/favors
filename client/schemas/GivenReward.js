var mongoose = require('mongoose');
var args = require('./args.js')

mongoose.connect(args.connectionString);

var Schema = mongoose.Schema;

var givenRewardSchema = new Schema({
	userId: {type: mongoose.ObjectId, required: true},
	rewardId: {type: mongoose.ObjectId, required: true},
	dateRedeemed: {type:Date, required:true}
    });

// export givenReward as a class called givenReward
module.exports = mongoose.model('givenreward', givenRewardSchema);