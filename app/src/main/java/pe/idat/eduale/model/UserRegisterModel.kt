package pe.idat.eduale.model

data class UserRegisterModel (
    val estado: String,
    val nickname: String,
    val clave:String,

    val tipoAcceso: UserAccessModel,
    val cliente: ClientModel,
)