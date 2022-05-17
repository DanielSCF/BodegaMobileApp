package pe.idat.eduale.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.idat.eduale.R
import pe.idat.eduale.model.CartModel

class CartAdapter (val cartList: MutableList<CartModel>): RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return CartViewHolder(layoutInflater.inflate(R.layout.item_cart, parent, false))
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = cartList.size
}