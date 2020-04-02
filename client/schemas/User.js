var mongoose = require('mongoose');
var args = require('./args.js')

mongoose.connect(args.connectionString);

var Schema = mongoose.Schema;

var userSchema = new Schema({
    username: {type: String, required: true, unique: true},
	email: {type: String, required: true, unique: true},
	password: {type: String, required: true},
	photo: {type: String, required: false},
	rating: {type:Number, required: true},
	points: {type:Number, required: true}
    });

// export userSchema as a class called User
module.exports = mongoose.model('User', userSchema);