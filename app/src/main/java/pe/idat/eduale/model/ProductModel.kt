package pe.idat.eduale.model

data class ProductModel (
    val nombre:String?,
    val descripcion:String?,
    val preciocompra:Double?,
    val precioventa:Double?,
    val stock:Int?,
    val estado:String?,
    val imagen:String?,
    val marca:String?,
    val categoria:String?
)