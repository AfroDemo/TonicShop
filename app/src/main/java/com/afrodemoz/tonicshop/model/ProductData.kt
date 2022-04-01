package com.afrodemoz.tonicshop.model

class ProductData {
    var prodName : String? = null
    var prodPrice : String? = null
    var prodDetail : String? = null
    var prodImage : String? = null

    constructor(){}

    constructor(
        prodName:String?,
        prodPrice:String?,
        prodDetail:String?,
        prodImage:String?
    ){
        this.prodName = prodName
        this.prodPrice = prodPrice
        this.prodDetail = prodDetail
        this.prodImage = prodImage
    }
}