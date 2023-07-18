var str="Hello";
var arr=[1,2,3,4,5];
var num=30;
var d=20.2;
var obj={"one":1,"Two":2};
var bool=true;
var date=new Date()
var map=new Map([["1","One"],["2","Two"]])

const sayHello=()=>{console.log("Hello")}


var depth = 31;

var now=0;
var day=0;
while(depth>=now){
    day++;
    now=now+7;
    if(now>depth){
        break;
    }else{
        now=now-2;
    }
}

console.log(day)