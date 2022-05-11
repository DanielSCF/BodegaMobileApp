package pe.idat.eduale.model

data class ProductModel(
    val productoid: Int?,
    val nombre: String?,
    val descripcion: String?,
    val preciocompra: Double?,
    val precioventa: Double?,
    val stock: Int?,
    val estado: String?,
    val imagen: String?,

    val categoria: CategoriaModel?,
    val marca: MarcaModel?,
    val lote: LoteModel?

)