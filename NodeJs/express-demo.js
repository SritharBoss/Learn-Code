//core
const path = require("path");
//3rd party
const express = require("express");
//Exported
const adminRoute = require("./Routes/shop");

const app = express();
app.use(express.static(path.join(__dirname,'public')))
app.use(adminRoute.route);
app.set('view engine','pug')
app.set('views','views')

app.listen(3000);