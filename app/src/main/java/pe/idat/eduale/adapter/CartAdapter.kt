package pe.idat.eduale.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.idat.eduale.R
import pe.idat.eduale.room.cart.CartModel

class CartAdapter (var itemList:MutableList<CartModel>): RecyclerView.Adapter<CartViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CartViewHolder(layoutInflater.inflate(R.layout.item_cart, parent, false))
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = itemList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = itemList.size


    fun setItems(cartList:MutableList<CartModel>){
        this.itemList = cartList
        notifyDataSetChanged()
    }

    fun newItem(cartModel: CartModel){
        itemList.add(cartModel)
        notifyDataSetChanged()
    }

    fun deleteItem(cartModel: CartModel){
        val index = itemList.indexOf(cartModel)
        if (index!=1){
            itemList.removeAt(index)
            notifyDataSetChanged()
        }
    }
}