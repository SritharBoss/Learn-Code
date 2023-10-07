let event=require('events')

var emitter=new event.EventEmitter()

emitter.on('Start',()=>{
    console.log("Connection Started")
})

emitter.emit('Start')

/** WAITING TIME
function waitForSometime(time){
    var start=new Date()
    while(start>new Date()-time){}
}
*/

/** READER

var fs=require('fs')
var data=''
var stream=fs.createReadStream('/home/srithar/test')
stream.setEncoding('utf-8')
stream.on('data',(chunk)=>{
    console.log(chunk.length)
    stream.close()
})

 */

/** WRITE STREAM
 var fs=require('fs')
var data=''
var stream=fs.createWriteStream('/home/srithar/test')
stream.write("This is written from NodeJS")
stream.end()
 */