var fs=require('fs')
var zlib=require('zlib')

fs.createReadStream('/home/srithar/test').pipe(zlib.createGzip()).pipe(fs.createWriteStream('/home/srithar/input.txt.gz'))