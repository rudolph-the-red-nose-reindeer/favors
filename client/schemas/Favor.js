var mongoose = require('mongoose');
var args = require('./args.js')

mongoose.connect(args.connectionString);

var Schema = mongoose.Schema;

var favorSchema = new Schema({
	userId: {type: mongoose.ObjectId, required: true},
    acceptedBy: {type: mongoose.ObjectId},
	datePosted: {type: Date, required: true},
	urgency: {type: Number, required: true},
	location: {type: String, required: false},
	details: {type: String, required: true},
    lat: {type: Number, required: false},
    lon: {type: Number, required: false}
	category: {type: String, required: true}
    });

// export favorSchema as a class called favor
module.exports = mongoose.model('favor', favorSchema);
