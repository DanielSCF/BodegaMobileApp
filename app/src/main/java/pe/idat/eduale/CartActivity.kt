package pe.idat.eduale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import pe.idat.eduale.adapter.CartAdapter
import pe.idat.eduale.databinding.ActivityCartBinding
import pe.idat.eduale.model.ProductModel
import pe.idat.eduale.room.cart.CartApp
import pe.idat.eduale.room.cart.CartModel
import pe.idat.eduale.room.cart.onItemListener

class CartActivity : AppCompatActivity(), onItemListener {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var mGridLayout: GridLayoutManager
    var itemList = mutableListOf<CartModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            startActivity(Intent(this, ProductActivity::class.java))
        }

        setCartRecyclerView()
    }

    private fun setCartRecyclerView(){
        cartAdapter = CartAdapter(mutableListOf(), this@CartActivity)
        mGridLayout = GridLayoutManager(this,1)

        getCartList()
        setTotal()

        binding.recyclerCart.apply{
            setHasFixedSize(true)
            adapter = cartAdapter
            layoutManager = mGridLayout
        }
    }

    private fun getCartList(){
        doAsync {
            val items = CartApp.database.cartDao().cartList()
            uiThread {
                itemList.addAll(items)
                cartAdapter.setItems(itemList)
            }
        }
    }

    private fun setTotal(){
        doAsync {
            val items = CartApp.database.cartDao().cartList()
            val total = items.sumOf { it.precio }

            uiThread {
                binding.txtTotal.text = "Total: S/." + total
            }
        }
    }

    //Eliminar de la lista
    private fun deleteItem(cartModel: CartModel){
        doAsync {
            CartApp.database.cartDao().deleteItem(cartModel)
            uiThread {
                itemList.remove(cartModel)
                cartAdapter.deleteItem(cartModel)
            }
        }
    }

    override fun onDeleteClick(position: Int) {
        val item = itemList.get(position)
        deleteItem(item)

        finish()
        overridePendingTransition(0,0)
        startActivity(Intent(this, CartActivity::class.java))
        overridePendingTransition(0,0)
    }

}