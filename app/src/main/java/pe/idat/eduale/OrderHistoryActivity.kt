package pe.idat.eduale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import pe.idat.eduale.adapter.OrderAdapter
import pe.idat.eduale.databinding.ActivityOrderHistoryBinding
import pe.idat.eduale.model.OrderDetailModel
import pe.idat.eduale.network.OrderDetailService
import pe.idat.eduale.network.RetroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderHistoryActivity : AppCompatActivity(), OnOrderListener {

    private lateinit var binding: ActivityOrderHistoryBinding
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    var orderList = mutableListOf<OrderDetailModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            sendUserData()
        }
        initItems()
    }

    private fun initItems(){
        orderAdapter = OrderAdapter(mutableListOf(), false,this@OrderHistoryActivity)
        gridLayoutManager = GridLayoutManager(this, 1)

        getMyData()

        binding.recyclerOrderHistory.apply {
            setHasFixedSize(true)
            adapter = orderAdapter
            layoutManager = gridLayoutManager
        }
    }

    private fun getMyData() {
        val objetoIntent: Intent = intent
        val ClienteID = objetoIntent.getStringExtra("ClienteID").toString().toInt()

        val retrofitData = RetroInstance().getRetroInstance().create(OrderDetailService::class.java)

        retrofitData.getOrderByClientId(ClienteID).enqueue(object :
            Callback<List<OrderDetailModel>?> {
            override fun onResponse(
                call: Call<List<OrderDetailModel>?>,
                response: Response<List<OrderDetailModel>?>
            ) {
                val responseBody = response.body()

                val orders = responseBody ?: emptyList()

                orderList.addAll(orders)

                orderAdapter = OrderAdapter(orderList, false, this@OrderHistoryActivity)
                orderAdapter.notifyDataSetChanged()
                binding.recyclerOrderHistory.adapter = orderAdapter

            }
            override fun onFailure(call: Call<List<OrderDetailModel>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
            }

        })
    }

    private fun sendUserData(){
        val value = Intent(this, OrderPendientActivity::class.java)

        val objetoIntent: Intent = intent
        val ClienteID = objetoIntent.getStringExtra("ClienteID")
        val UsuarioID = objetoIntent.getStringExtra("UsuarioID")
        value.putExtra("ClienteID", ClienteID)
        value.putExtra("UsuarioID", UsuarioID)
        startActivity(value)
    }

    override fun onOrderClick(position: Int) {
        TODO("Not yet implemented")
    }


}