package pe.idat.eduale.network

import pe.idat.eduale.model.OrderModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderService {
    @POST("pedido")
    fun guardarpedido(@Body requestPedido: OrderModel): Call<OrderModel>
}