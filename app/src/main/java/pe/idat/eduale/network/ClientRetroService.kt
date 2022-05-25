package pe.idat.eduale.network

import pe.idat.eduale.model.ClientModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ClientRetroService {
    @GET("cliente/{id}")
    fun buscarcliente(@Path("id") id:Int): Call<ClientModel>

    @PUT("cliente")
    fun editarcliente(@Body clientModel: ClientModel): Call<ClientModel>

}