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
            val objetoIntent: Intent = intent
            var ClienteID = objetoIntent.getStringExtra("ClienteID")
            var UsuarioID = objetoIntent.getStringExtra("UsuarioID")

            val value = Intent(this, ProductActivity::class.java)
            value.putExtra("ClienteID",ClienteID)
            value.putExtra("UsuarioID",UsuarioID)
            startActivity(value)
        }

        setCartRecyclerView()
        registerOrder()
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
            val total = items.sumOf { it.subtotal }

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

        val objetoIntent: Intent = intent
        var ClienteID = objetoIntent.getStringExtra("ClienteID")
        var UsuarioID = objetoIntent.getStringExtra("UsuarioID")

        val value = Intent(this, CartActivity::class.java)
        value.putExtra("ClienteID",ClienteID)
        value.putExtra("UsuarioID",UsuarioID)
        startActivity(value)

        overridePendingTransition(0,0)
    }

    private fun registerOrder(){
        val objetoIntent: Intent = intent
        var ClienteID = objetoIntent.getStringExtra("ClienteID")
        var UsuarioID = objetoIntent.getStringExtra("UsuarioID")



    }

}