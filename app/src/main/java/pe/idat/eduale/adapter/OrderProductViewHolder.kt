package pe.idat.eduale.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.idat.eduale.databinding.ItemOrderProductBinding
import pe.idat.eduale.model.OrderDetailModel

class OrderProductViewHolder(view:View): RecyclerView.ViewHolder(view) {
    var binding = ItemOrderProductBinding.bind(view)

    fun renderProduct(orderDetailModel: OrderDetailModel){
        binding.txtNombreProducto.text = orderDetailModel.producto.nombre + " - " + orderDetailModel.producto.descripcion
        binding.txtMarca.text = orderDetailModel.producto.marca!!.nombre
        binding.txtCantidad.text = orderDetailModel.cantidad.toString()
        binding.txtSubtotal.text = orderDetailModel.subtotal.toString()

        Glide.with(binding.imgProducto.context).load(orderDetailModel.producto.imagen).into(binding.imgProducto)
    }
}