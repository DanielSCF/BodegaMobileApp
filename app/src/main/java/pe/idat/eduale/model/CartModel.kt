package pe.idat.eduale.model

data class CartModel (
    val cantidad:Int,
    val total:Double,

    val product:ProductModel
)