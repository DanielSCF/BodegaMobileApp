package pe.idat.eduale.model

data class UserModel(
    val usuarioID: Int?,
    val nickname: String,
    var clave: String,
    val estado: String,

    val trabajador: Any?,
    val tipoAcceso: UserAccessModel,
    val cliente: ClientModel
)
