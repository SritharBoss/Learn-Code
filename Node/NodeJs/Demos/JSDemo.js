let string="This is String''"
let string1='This is " String'
let string2=`This is Backtick string ${string}`
let bool=true
let num=20
let bigNum=20n


console.log(null === +"\n0\n")


function printAll(...value){

    value.forEach(a=>{
        console.log(a)
    })

}

function printNumericConversion(){
    console.log("---Numeric conversion ----")
    console.log("NAN\t\t--> "+Number(NaN))
    console.log("null\t\t--> "+Number(null))
    console.log("undefined\t--> "+Number(undefined))
    console.log("true\t\t--> "+Number(true))
    console.log('"Strings"\t--> '+Number("Strings"))
    console.log('"7"\t\t--> '+Number("7"))
}

function printBooleanConversion(){
    console.log("---Boolean conversion ----")
    console.log("NAN\t\t--> "+Boolean(NaN))
    console.log("null\t\t--> "+Boolean(null))
    console.log("undefined\t--> "+Boolean(undefined))
    console.log("true\t\t--> "+Boolean(true))
    console.log('"Strings"\t--> '+Boolean("Strings"))
    console.log('"0"\t\t--> '+Boolean("0"))
    console.log('1\t\t--> '+Boolean(1))
}