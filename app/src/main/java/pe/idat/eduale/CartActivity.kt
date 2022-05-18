package pe.idat.eduale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import pe.idat.eduale.adapter.CartAdapter
import pe.idat.eduale.databinding.ActivityCartBinding
import pe.idat.eduale.room.cart.CartApp

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var mAdapter: CartAdapter
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
        mAdapter = CartAdapter(mutableListOf())
        mGridLayout = GridLayoutManager(this,1)
        getCartList()

        binding.recyclerCart.apply{
            setHasFixedSize(true)
            adapter = mAdapter
            layoutManager = mGridLayout
        }
    }

    private fun getCartList(){
        val items = CartApp.database.cartDao().cartList()
        mAdapter.setItems(items)
    }

}