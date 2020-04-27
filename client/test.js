var fs = require('fs');
var sharp = require('sharp');

var img = fs.readFileSync('./test.jpg');
console.log(img);

var resizer = sharp()
    .resize({
        width: 250,
        height: 250
    });


//console.log(out);