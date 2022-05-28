package pe.idat.eduale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import pe.idat.eduale.adapter.OrderAdapter
import pe.idat.eduale.adapter.ProductAdapter
import pe.idat.eduale.databinding.ActivityOrderPendientBinding
import pe.idat.eduale.model.OrderModel
import pe.idat.eduale.model.ProductModel
import pe.idat.eduale.network.OrderService
import pe.idat.eduale.network.ProductService
import pe.idat.eduale.network.RetroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderPendientActivity : AppCompatActivity(), OnOrderListener {

    private lateinit var binding:ActivityOrderPendientBinding
    private lateinit var orderAdapter:OrderAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    var orderList = mutableListOf<OrderModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderPendientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            val value = Intent(this, ProductActivity::class.java)

            val objetoIntent: Intent = intent
            val ClienteID = objetoIntent.getStringExtra("ClienteID")
            val UsuarioID = objetoIntent.getStringExtra("UsuarioID")
            value.putExtra("ClienteID", ClienteID)
            value.putExtra("UsuarioID", UsuarioID)
            startActivity(value)
        }

        initItems()

    }

    private fun initItems(){
        orderAdapter = OrderAdapter(mutableListOf(), this@OrderPendientActivity)
        gridLayoutManager = GridLayoutManager(this, 1)

        getMyData()

        binding.recyclerOrder.apply {
            setHasFixedSize(true)
            adapter = orderAdapter
            layoutManager = gridLayoutManager
        }
    }

    private fun getMyData() {
        val retrofitData =
            RetroInstance().getRetroInstance().create(OrderService::class.java)

        retrofitData.getPedidos().enqueue(object : Callback<List<OrderModel>?> {
            override fun onResponse(
                call: Call<List<OrderModel>?>,
                response: Response<List<OrderModel>?>
            ) {
                val responseBody = response.body()

                val orders = responseBody ?: emptyList()

                orderList.addAll(orders)

                orderAdapter = OrderAdapter(orderList, this@OrderPendientActivity)
                orderAdapter.notifyDataSetChanged()
                binding.recyclerOrder.adapter = orderAdapter

            }
            override fun onFailure(call: Call<List<OrderModel>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
            }

        })
    }

    override fun onOrderClick(position: Int) {
        TODO("Not yet implemented")
    }
}