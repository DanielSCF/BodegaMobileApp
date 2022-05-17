package pe.idat.eduale.network

import pe.idat.eduale.model.ProductModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductRetroService {
    @GET("productos")
    fun getProductList(): Call<List<ProductModel>>

    @GET("productos/buscar/{nombre}")
    fun getProductByName(@Path("nombre") nombre:String): Call<List<ProductModel>>

    @GET("productos/{productoid}")
    fun getProductById(@Path("productoid") productoid:Int): Call<ProductModel>

    @GET("productos/{usuarioid}/{productoid}")
    fun userAddProductoCart(@Path("usuarioid") usuarioid:Int, @Path("productoid") productoid:Int):Call<List<ProductModel>>

}