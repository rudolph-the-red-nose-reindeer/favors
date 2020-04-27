// tool to create hashed and salted passwords
var random = require('crypto-random-string');
var pbkdf2 = require('pbkdf2');

const hashAndSalt = (password) => {
        var salt = random({length: 64});
        var hash = pbkdf2.pbkdf2Sync(password, salt, 1, 32, 'sha512').toString('hex');

        return { salt: salt,
                 hash: hash };
}

const authenticate = (password, salt, hash) => {
    encode = pbkdf2.pbkdf2Sync(password, salt, 1, 32, 'sha512').toString('hex');

    return encode === hash;
}


module.exports = { hashAndSalt, authenticate }