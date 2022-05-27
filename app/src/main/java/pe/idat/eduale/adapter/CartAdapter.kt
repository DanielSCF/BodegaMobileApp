package pe.idat.eduale.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.idat.eduale.R
import pe.idat.eduale.room.cart.CartModel
import pe.idat.eduale.room.cart.OnItemListener

class CartAdapter (var itemList:MutableList<CartModel>, private val OnItemListener: OnItemListener): RecyclerView.Adapter<CartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CartViewHolder(layoutInflater.inflate(R.layout.item_cart, parent, false))
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = itemList[position]
        holder.render(item)

        holder.deleteItem.setOnClickListener {
            OnItemListener.onDeleteClick(position)
        }

        holder.addQuantity.setOnClickListener{
            OnItemListener.onAddClick(position)
        }

        holder.subtractQuantity.setOnClickListener{
            OnItemListener.onSubtractClick(position)
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun setItems(cartList: MutableList<CartModel>) {
        this.itemList = cartList
        notifyDataSetChanged()
    }

    fun newItem(cartModel: CartModel) {
        itemList.add(cartModel)
        notifyDataSetChanged()
    }

    fun deleteItem(cartModel: CartModel) {
        val index = itemList.indexOf(cartModel)
        if (index != 1) {
            itemList.remove(cartModel)
            notifyDataSetChanged()
        }
    }

    fun clearCart() {
        itemList.clear()
        notifyDataSetChanged()
    }

    fun sumOneItem(cartModel: CartModel, nuevaCantidad: Int) {
        itemList.find { item -> item == cartModel }?.cantidad = nuevaCantidad + 1

        val cantidad = itemList.find { item -> item == cartModel }?.cantidad
        val precio = itemList.find { item -> item == cartModel }?.precio

        itemList.find {item -> item == cartModel }?.subtotal = cantidad!! * precio!!
        notifyDataSetChanged()
    }

    fun subtractOneItem(cartModel: CartModel, nuevaCantidad: Int) {
        itemList.find { item -> item == cartModel }?.cantidad = nuevaCantidad - 1

        val cantidad = itemList.find { item -> item == cartModel }?.cantidad
        val precio = itemList.find { item -> item == cartModel }?.precio

        itemList.find {item -> item == cartModel }?.subtotal = cantidad!! * precio!!
        notifyDataSetChanged()
    }
}