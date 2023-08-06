const express=require('express')

const adminRoute=require('./Routes/admin')

const app=express()
app.use(adminRoute.route)

app.listen(3000)