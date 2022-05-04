package pe.idat.eduale.network

import pe.idat.eduale.model.ProductModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductRetroService {
    @GET("productos")
    fun getProductList(): Call<List<ProductModel>>

    @GET("productos/{nombre}")
    fun getProductByName(@Path("nombre") nombre:String): Call<List<ProductModel>>

}