package pe.idat.eduale.network

import pe.idat.eduale.model.UserLoginRequest
import pe.idat.eduale.model.UserResponse
import pe.idat.eduale.model.UserModel
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @GET("usuarios/{usuarioID}")
    fun getUserById(@Path("usuarioID") usuarioID:Int): Call<UserModel>

    @POST("usuarios/login")
    fun login (@Body userRequest: UserLoginRequest): Call<UserResponse>

    @POST("usuarios")
    fun guardarusuario(@Body userModel: UserModel): Call<UserModel>

    @PUT("usuarios")
    fun editUser(@Body userModel: UserModel): Call<UserModel>

}