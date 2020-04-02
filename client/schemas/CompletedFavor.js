var mongoose = require('mongoose');
var args = require('./args.js')

mongoose.connect(args.connectionString);

var Schema = mongoose.Schema;

var completedFavorSchema = new Schema({
	dateCompleted: {type: Date, required: true},
	favorDoer: {type: Number, required: true},
    });

// export completedFavorSchema as a class called completedFavor
module.exports = mongoose.model('completedFavor', completedFavorSchema);
