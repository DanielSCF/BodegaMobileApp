package pe.idat.eduale.adapter

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.idat.eduale.databinding.ItemOrderBinding
import pe.idat.eduale.model.OrderDetailModel
import pe.idat.eduale.model.OrderModel

class OrderViewHolder (view:View): RecyclerView.ViewHolder(view) {
    var binding = ItemOrderBinding.bind(view)
    var btnCancelOrder = binding.btnCancelOrder
    var orderCard = binding.OrderCard

    fun render(orderDetailModel: OrderDetailModel){
        val fecha = orderDetailModel.pedido.fecha.toString()
        val (dia, hora) = fecha!!.split(" ")

        binding.txtFecha.text = dia
        binding.txtHora.text = hora
        binding.txtTotal.text = orderDetailModel.pedido.total.toString()
        binding.txtEstado.text = orderDetailModel.pedido.estado

        binding.txtNombreProducto.text = orderDetailModel.producto.nombre
        binding.txtMarca.text = orderDetailModel.producto.marca!!.nombre
        binding.txtCantidad.text = "Cantidad: " + orderDetailModel.cantidad.toString()
        binding.txtSubtotal.text = "S/. " + orderDetailModel.subtotal.toString()

        Glide.with(binding.imgProducto.context).load(orderDetailModel.producto.imagen).into(binding.imgProducto)
    }
}