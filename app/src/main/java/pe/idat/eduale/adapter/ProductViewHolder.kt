package pe.idat.eduale.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.idat.eduale.databinding.ItemProductBinding
import pe.idat.eduale.model.ProductModel
import pe.idat.eduale.onProductListener

class ProductViewHolder (view: View): RecyclerView.ViewHolder(view){
    var binding = ItemProductBinding.bind(view)
    var addButton = binding.btnAddToCart

    fun render(productModel: ProductModel){
        //replaceFirstChat
        binding.txtNombreProducto.text = productModel.nombre+ " - " + productModel.descripcion
        binding.txtPrecio.text = "S/." + productModel.precioventa.toString()
        binding.txtMarca.text = productModel.marca?.nombre.toString()
        Glide.with(binding.ImgProduct.context).load(productModel.imagen).into(binding.ImgProduct)
    }
}