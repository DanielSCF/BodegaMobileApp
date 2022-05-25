package pe.idat.eduale.model

data class UserResponse (
    val data: String?,
    val error: Any?,
    val status: Int?,
    val usuario: UserModel?
)