package pe.idat.eduale.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.idat.eduale.databinding.ItemProductBinding
import pe.idat.eduale.model.ProductModel

class ProductViewHolder (view: View): RecyclerView.ViewHolder(view){
    var binding = ItemProductBinding.bind(view)

    fun render(productModel: ProductModel){
        binding.txtNombreProducto.text = productModel.nombre+ " - " + productModel.descripcion
        binding.txtPrecio.text = "S/." + productModel.precioventa.toString()
        binding.txtMarca.text = productModel.marca

        Glide.with(binding.ImgProduct.context).load(productModel.imagen).into(binding.ImgProduct)
    }
}