package pe.idat.eduale.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pe.idat.eduale.OnOrderListener
import pe.idat.eduale.OrderPendientActivity
import pe.idat.eduale.R
import pe.idat.eduale.model.OrderDetailModel
import pe.idat.eduale.model.OrderModel

class OrderAdapter(val orderList: MutableList<OrderDetailModel>, val pendiente:Boolean, private val OnOrderListener: OnOrderListener) :
    RecyclerView.Adapter<OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return OrderViewHolder(layoutInflater.inflate(R.layout.item_order, parent, false))
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = orderList[position]
        holder.render(item)

        val recyclerProduct = holder.recyclerProduct

        if(pendiente) {
            holder.btnCancelOrder.setOnClickListener {
                OnOrderListener.onOrderClick(position)
            }
        } else {
            holder.btnCancelOrder.visibility = View.GONE
        }
    }
    override fun getItemCount(): Int = orderList.size
}