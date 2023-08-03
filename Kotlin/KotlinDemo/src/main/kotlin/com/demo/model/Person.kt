package com.demo.model

data class Person(
    var firstName: String="N/A", var lastName: String="N/A", var age: Int=0, var address: Address=Address()
)

data class Address(var add1: String, var add2: String, var state: String, var pinCode: Int) {
    constructor() : this("Address1", "Address2", "State", 123456)
}