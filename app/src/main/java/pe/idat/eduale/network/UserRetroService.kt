package pe.idat.eduale.network

import pe.idat.eduale.model.UserLoginRequest
import pe.idat.eduale.model.UserLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserRetroService {

    @POST("usuarios/login")
    fun login (@Body userRequest: UserLoginRequest): Call<UserLoginResponse>

}