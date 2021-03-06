package pe.idat.eduale.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.idat.eduale.databinding.ItemCartBinding
import pe.idat.eduale.room.cart.CartModel

class CartViewHolder (view:View): RecyclerView.ViewHolder(view){
    var binding = ItemCartBinding.bind(view)
    var deleteItem = binding.btnDelete
    var addQuantity = binding.btnAdd
    var subtractQuantity = binding.btnMinus

    fun render(cartModel: CartModel){
        binding.txtNombreProducto.text = cartModel.nombre
        binding.txtPrecio.text = "Precio: S/." + cartModel.subtotal.toString()
        binding.txtMarca.text = cartModel.marca
        binding.txtAmount.text = cartModel.cantidad.toString()

        Glide.with(binding.ImgProduct.context).load(cartModel.imagen).into(binding.ImgProduct)
    }
}