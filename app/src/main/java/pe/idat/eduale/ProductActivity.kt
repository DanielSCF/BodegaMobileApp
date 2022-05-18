package pe.idat.eduale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import pe.idat.eduale.adapter.ProductAdapter
import pe.idat.eduale.databinding.ActivityProductBinding
import pe.idat.eduale.model.ProductModel
import pe.idat.eduale.network.ProductRetroService
import pe.idat.eduale.network.RetroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActivity : AppCompatActivity() , SearchView.OnQueryTextListener{

    private lateinit var binding:ActivityProductBinding
    private lateinit var myAdapter: ProductAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    var productsList = mutableListOf<ProductModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerProducts.setHasFixedSize(true)
        gridLayoutManager = GridLayoutManager(this, 2)
        binding.recyclerProducts.layoutManager = gridLayoutManager

        binding.txtSearch.setOnQueryTextListener(this)

        binding.btnShoppingCart.setOnClickListener{
            startActivity(Intent(this, CartActivity::class.java))
        }

        getMyData()
    }

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

                myAdapter = ProductAdapter(productsList)
                myAdapter.notifyDataSetChanged()
                binding.recyclerProducts.adapter = myAdapter

            }

            override fun onFailure(call: Call<List<ProductModel>?>, t: Throwable) {
                Log.d("MainActivity","onFailure: "+ t.message)
            }

        })
    }

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
                myAdapter = ProductAdapter(productsList)
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
}