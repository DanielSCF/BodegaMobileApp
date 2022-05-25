package pe.idat.eduale.network

import pe.idat.eduale.model.OrderDetailModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderDetailService {
    @POST("detallepedido")
    fun registrarPedido(@Body requestDetallePedido: OrderDetailModel): Call<OrderDetailModel>
}