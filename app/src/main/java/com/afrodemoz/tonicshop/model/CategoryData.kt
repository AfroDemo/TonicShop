package com.afrodemoz.tonicshop.model

class CategoryData {
    var categoryName : String? = null
    var categoryPrice : String? = null
    var categoryLocation : String? = null
    var categoryDetail : String? = null
    var categoryImage : String? = null

    constructor(){}

    constructor(
        categoryName:String?,
        categoryPrice:String?,
        categoryLocation:String?,
        categoryDetail:String?,
        categoryImage:String?
    ){
        this.categoryName = categoryName
        this.categoryPrice = categoryPrice
        this.categoryDetail = categoryDetail
        this.categoryLocation = categoryLocation
        this.categoryImage = categoryImage
    }
}