const express=require('express')

const route=express.Router()

route.use(parser)
route.get('/',(req,res,next)=>{
    res.send('<form action="/users" method="POST"><input type="text" name="product"/><button>Submit</button></form>')
})

route.post('/users',(req,res,next)=>{
    
})

route.get('/users',(req,res,next)=>{
})

module.exports={route}