var password = require('./tools/password.js');


console.log(password.hashAndSalt('test'));

console.log(password.authenticate('test', 'b1d022507eba90787641f197c3e273177fa4ee85b90f25ba57236c96bc29ccaf', 'd00c9071e801dff011b6e2c23111ccccc0dbc22ccaf33ad45a02d555e9b14f8c'))