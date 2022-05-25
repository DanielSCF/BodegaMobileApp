package pe.idat.eduale.model

data class OrderDetailModel(
    val cantidad: Int,
    val pedido: OrderModel,
    val precio_venta: Double,
    val producto: ProductModel,
    val subtotal: Double
)
