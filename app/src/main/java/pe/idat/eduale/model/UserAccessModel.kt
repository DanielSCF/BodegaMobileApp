package pe.idat.eduale.model

data class UserAccessModel (
    val tipoAccesoID: Int,
    val nombre: String?,
    val descripcion: String?,
    val estado: String?,
)