package pe.idat.eduale

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import pe.idat.eduale.adapter.ProductAdapter
import pe.idat.eduale.databinding.ActivityProductBinding
import pe.idat.eduale.model.ProductModel
import pe.idat.eduale.network.ProductRetroService
import pe.idat.eduale.network.RetroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductActivity : AppCompatActivity() , SearchView.OnQueryTextListener{

    private lateinit var binding:ActivityProductBinding
    private lateinit var myAdapter: ProductAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerProducts.setHasFixedSize(true)
        gridLayoutManager = GridLayoutManager(this, 2)
        binding.recyclerProducts.layoutManager = gridLayoutManager

        getMyData()
    }

    private fun getMyData(){
        val retrofitData = RetroInstance().getRetroInstance().create(ProductRetroService::class.java)

        retrofitData.getProductList().enqueue(object: Callback<List<ProductModel>?> {
            override fun onResponse(
                call: Call<List<ProductModel>?>,
                response: Response<List<ProductModel>?>
            ) {
                val responseBody = response.body()!!

                myAdapter = ProductAdapter(responseBody)
                myAdapter.notifyDataSetChanged()
                binding.recyclerProducts.adapter = myAdapter

            }

            override fun onFailure(call: Call<List<ProductModel>?>, t: Throwable) {
                Log.d("MainActivity","onFailure: "+ t.message)
            }

        })
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        TODO("Not yet implemented")
    }

}