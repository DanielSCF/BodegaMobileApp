package pe.idat.eduale.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.idat.eduale.databinding.ItemCartBinding
import pe.idat.eduale.model.CartModel

class CartViewHolder (view:View): RecyclerView.ViewHolder(view){
    var binding = ItemCartBinding.bind(view)

    fun render(cartModel: CartModel){
        //Product information
        binding.txtNombreProducto.text = cartModel.product.nombre+ " - " + cartModel.product.descripcion
        binding.txtPrecio.text = "S/." + cartModel.product.precioventa.toString()

        val initialAmount = 1
        binding.txtAmount.setText(initialAmount)

        Glide.with(binding.ImgProduct.context).load(cartModel.product.imagen).into(binding.ImgProduct)
    }
}