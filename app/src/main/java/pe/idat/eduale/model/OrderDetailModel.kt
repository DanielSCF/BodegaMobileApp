package pe.idat.eduale.model

data class OrderDetailModel(
    val cantidad: Int,
    val precio_venta: Double,
    val subtotal: Double,
    val producto: ProductModel,
    val pedido: OrderModel
)
