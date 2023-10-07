//core
const path = require("path");
//3rd party
const express = require("express");

const route = express.Router();
//route.use(express.json());

const products = ['Test'];
route.use(express.urlencoded({ extended: true }));
route.get("/", (req, res, next) => {
  //res.sendFile(path.join(__dirname, "../", "views/home.html"));
  res.render('home',{prods:products})
});

route.post("/add-product", (req, res, next) => {
  var value=req.body.product;
  if(value!='' ){
    products.push(value);
  }
  res.redirect("/");
});

route.get("/add-product", (req, res, next) => {
  res.render('add-product')
});

//404
route.get((req,res,next)=>{
  res.render('404')
})

module.exports = { route };
