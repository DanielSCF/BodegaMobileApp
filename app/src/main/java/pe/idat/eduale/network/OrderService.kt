package pe.idat.eduale.network

import pe.idat.eduale.model.OrderModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface OrderService {

    @GET("pedido")
    fun getPedidos():Call<List<OrderModel>>

    @POST("pedido")
    fun guardarpedido(@Body requestPedido: OrderModel): Call<OrderModel>

    @PUT("pedido")
    fun editarpedido(@Body requestPedido: OrderModel):Call<OrderModel>
}