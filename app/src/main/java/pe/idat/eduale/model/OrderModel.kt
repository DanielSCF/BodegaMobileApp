package pe.idat.eduale.model

data class OrderModel (
    val pedidoID: Int?,
    val fecha: String?,
    val total: Double?,
    val modalidad: String?,
    var estado: String,
    val cliente: ClientModel?,
    val trabajador: Any?
)