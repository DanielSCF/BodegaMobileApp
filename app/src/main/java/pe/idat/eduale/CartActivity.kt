package pe.idat.eduale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import pe.idat.eduale.adapter.CartAdapter
import pe.idat.eduale.databinding.ActivityCartBinding
import pe.idat.eduale.room.cart.CartApp

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var mGridLayout: GridLayoutManager

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
        cartAdapter = CartAdapter(mutableListOf())
        mGridLayout = GridLayoutManager(this,1)
        getCartList()

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
                cartAdapter.setItems(items)
            }
        }
    }

}