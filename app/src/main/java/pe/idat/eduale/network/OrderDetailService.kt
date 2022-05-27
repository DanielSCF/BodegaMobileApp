package pe.idat.eduale.network

import pe.idat.eduale.model.OrderDetailModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderDetailService {
    @GET("detallepedido/{clienteid}")
    fun getOrderByClientId(@Path("clienteid") clienteid:Int): Call<List<OrderDetailModel>>

    @GET("detallepedido/{clienteid}/{estado}")
    fun getOrderByClientIdAndState(@Path("clienteid") clienteid:Int, @Path("estado") estado:String): Call<List<OrderDetailModel>>

    @POST("detallepedido")
    fun registrarPedido(@Body requestDetallePedido: OrderDetailModel): Call<OrderDetailModel>
}