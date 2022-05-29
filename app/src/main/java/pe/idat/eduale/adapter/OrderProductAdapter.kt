package pe.idat.eduale.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.idat.eduale.R
import pe.idat.eduale.model.OrderDetailModel

class OrderProductAdapter (val orderProductList:MutableList<OrderDetailModel>): RecyclerView.Adapter<OrderProductViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return OrderProductViewHolder(layoutInflater.inflate(R.layout.item_order_product, parent, false))
    }

    override fun onBindViewHolder(holder: OrderProductViewHolder, position: Int) {
        val item = orderProductList[position]
        holder.renderProduct(item)
    }

    override fun getItemCount(): Int = orderProductList.size

}