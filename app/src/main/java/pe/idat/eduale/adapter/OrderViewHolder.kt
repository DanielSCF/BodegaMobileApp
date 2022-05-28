package pe.idat.eduale.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import pe.idat.eduale.databinding.ItemOrderBinding
import pe.idat.eduale.model.OrderModel

class OrderViewHolder (view:View): RecyclerView.ViewHolder(view) {
    var binding = ItemOrderBinding.bind(view)
    var btnCancelOrder = binding.btnCancelOrder

    fun render(orderModel: OrderModel){
        val fecha = orderModel.fecha.toString()
        val (dia, hora) = fecha!!.split(" ")

        binding.txtFecha.text = dia
        binding.txtHora.text = hora
        binding.txtTotal.text = orderModel.total.toString()
    }
}