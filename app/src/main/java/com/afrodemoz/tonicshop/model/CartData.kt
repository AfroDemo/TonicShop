package com.afrodemoz.tonicshop.model

class CartData {
    var prodName : String? = null
    var prodPrice : String? = null
    var totalPrice : String? = null
    var prodCount : String? = null
    var prodImage : String? = null
    var sellName : String? = null
    var sellPhone : String? = null

    constructor(){}

    constructor(
        prodName:String?,
        prodPrice:String?,
        totalPrice:String?,
        prodCount:String?,
        prodImage:String?,
        sellName:String?,
        sellPhone:String?
    ){
        this.prodName = prodName
        this.prodPrice = prodPrice
        this.totalPrice = totalPrice
        this.prodCount = prodCount
        this.prodImage = prodImage
        this.sellName = sellName
        this.sellPhone = sellPhone
    }
}