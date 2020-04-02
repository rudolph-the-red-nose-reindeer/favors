var mongoose = require('mongoose');
var args = require('./args.js')

mongoose.connect(args.connectionString);

var Schema = mongoose.Schema;

var validateUsername = (username) => {
    var regex = /^[a-zA-Z0-9]+$/;
    return regex.test(username) && username.length <= 13;
}

var validateEmail = (email) => {
    var regex = /^[a-zA-Z0-9]+@([a-zA-Z0-9]+[.])+[a-zA-Z]+$/;
    return regex.test(email);
};

var userSchema = new Schema({
    username: {type: String, required: true, unique: true, validate: [validateUsername, 'Please enter a valid username (alphanumeric, 13 characters max).']},
	email: {type: String, required: true, unique: true, validate: [validateEmail, 'Please enter a valid email address.']},
	salt: {type: String, required: true},
    hash: {type: String, required: true},
	photo: {type: String, required: false},
    bio: {type: String, required: false},
	rating: {type:Number, required: true},
	points: {type:Number, required: true}
});

userSchema.index({
    username: 1
}, {
    collation: {
        locale: 'en',
        strength: 2
    },
    unique: true
});
userSchema.index({
    email: 1
}, {
    collation: {
        locale: 'en',
        strength: 2
    },
    unique: true
});

// export userSchema as a class called User
module.exports = mongoose.model('User', userSchema);