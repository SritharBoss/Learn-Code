const http=require('http')

const routes=require('./routes')

http.createServer(routes.handler).listen(3000)