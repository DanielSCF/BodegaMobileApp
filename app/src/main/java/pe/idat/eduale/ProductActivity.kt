package pe.idat.eduale

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import pe.idat.eduale.adapter.CartAdapter
import pe.idat.eduale.adapter.ProductAdapter
import pe.idat.eduale.databinding.ActivityProductBinding
import pe.idat.eduale.model.ProductModel
import pe.idat.eduale.network.ProductRetroService
import pe.idat.eduale.network.RetroInstance
import pe.idat.eduale.room.cart.CartApp
import pe.idat.eduale.room.cart.CartModel
import pe.idat.eduale.room.cart.onItemListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent as Intent

class ProductActivity : AppCompatActivity() , SearchView.OnQueryTextListener, onProductListener, onItemListener{

    private lateinit var binding:ActivityProductBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var myAdapter: ProductAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var cartAdapter:CartAdapter
    var productsList = mutableListOf<ProductModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cartAdapter = CartAdapter(mutableListOf(), this@ProductActivity)

        binding.recyclerProducts.setHasFixedSize(true)
        gridLayoutManager = GridLayoutManager(this, 2)
        binding.recyclerProducts.layoutManager = gridLayoutManager

        binding.txtSearch.setOnQueryTextListener(this)

        binding.btnShoppingCart.setOnClickListener{
            startActivity(Intent(this, CartActivity::class.java))
        }

        getMyData()


        binding.apply {
            toggle = ActionBarDrawerToggle(this@ProductActivity, drawerLayout, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.userInformation -> {
                        Toast.makeText(this@ProductActivity, "Ejemplo 1", Toast.LENGTH_SHORT).show()
                    }
                    R.id.clientInformation -> {
                        val intent = Intent(this@ProductActivity, ClientInformationActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    R.id.orderInformation -> {
                        Toast.makeText(this@ProductActivity, "third Item Clicked", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
        }
    }

    //Drawer menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }

    //Listado de productos
    private fun getMyData(){
        val retrofitData = RetroInstance().getRetroInstance().create(ProductRetroService::class.java)

        retrofitData.getProductList().enqueue(object: Callback<List<ProductModel>?> {
            override fun onResponse(
                call: Call<List<ProductModel>?>,
                response: Response<List<ProductModel>?>
            ) {
                val responseBody = response.body()

                val products = responseBody ?: emptyList()

                productsList.addAll(products)

                myAdapter = ProductAdapter(productsList,this@ProductActivity)
                myAdapter.notifyDataSetChanged()
                binding.recyclerProducts.adapter = myAdapter

            }

            override fun onFailure(call: Call<List<ProductModel>?>, t: Throwable) {
                Log.d("MainActivity","onFailure: "+ t.message)
            }

        })
    }

    //Busqueda de productos
    private fun searchProducts(query:String){
        val retrofitData = RetroInstance().getRetroInstance().create(ProductRetroService::class.java)

        retrofitData.getProductByName(query).enqueue(object: Callback<List<ProductModel>?> {
            override fun onResponse(
                call: Call<List<ProductModel>?>,
                response: Response<List<ProductModel>?>
            ) {
                val responseBody = response.body()

                val products = responseBody ?: emptyList()

                productsList.clear()
                productsList.addAll(products)
                myAdapter = ProductAdapter(productsList, this@ProductActivity )
                myAdapter.notifyDataSetChanged()
                binding.recyclerProducts.adapter = myAdapter

            }

            override fun onFailure(call: Call<List<ProductModel>?>, t: Throwable) {
                Log.d("MainActivity","onFailure: "+ t.message)
            }

        })

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrBlank()){
            searchProducts(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query.isNullOrBlank()){
            productsList.clear()
            getMyData()
        }
        return true
    }


    //Product button click
    override fun onProductClick(position: Int) {
        val product = productsList.get(position)

        val cantidad = 1
        val precio = product.precioventa
        val subtotal = cantidad * precio!!

        val cart = CartModel(
            nombre = product.nombre + " - " + product.descripcion,
            precio = precio,
            cantidad = cantidad,
            imagen = product.imagen!!,
            subtotal = subtotal,
            productId = product.productoid!!
        )
        registerItem(cart)
    }

    private fun registerItem(cartModel: CartModel){
        doAsync {
            CartApp.database.cartDao().addItem(cartModel)

            uiThread {
                cartAdapter.newItem(cartModel)
            }
        }
    }

    override fun onDeleteClick(position: Int) {
        TODO("Not yet implemented")
    }
}