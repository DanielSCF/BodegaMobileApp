package pe.idat.eduale.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.idat.eduale.databinding.ItemCartBinding
import pe.idat.eduale.model.ProductModel
import pe.idat.eduale.network.ProductRetroService
import pe.idat.eduale.network.RetroInstance
import pe.idat.eduale.room.cart.CartModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class CartViewHolder (view:View): RecyclerView.ViewHolder(view){
    var binding = ItemCartBinding.bind(view)
    lateinit var productModel:ProductModel

    private fun getProductById(id: Int?): ProductModel {
        val retrofitData = RetroInstance().getRetroInstance().create(ProductRetroService::class.java)

        try{
            retrofitData.getProductById(id!!).enqueue(object: Callback<ProductModel> {
                override fun onResponse(call: Call<ProductModel>, response: Response<ProductModel>) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        productModel = responseBody
                    }
                }
                override fun onFailure(call: Call<ProductModel>, t: Throwable) {
                    Log.d("MainActivity","onFailure: "+ t.message)
                }
            })
        }catch (e: IOException){
            Log.d("MainActivity","onFailure: "+ e.message)
        }

        return productModel
    }

    fun render(cartModel: CartModel){
        var productid:Int? = cartModel.productId
        var product:ProductModel = getProductById(productid)

        binding.txtNombreProducto.text = product.nombre+ " - " + product.descripcion
        binding.txtPrecio.text = "S/." + product.precioventa.toString()

        binding.txtAmount.setText(cartModel.cantidad!!)
        Glide.with(binding.ImgProduct.context).load(product.imagen).into(binding.ImgProduct)

    }
}