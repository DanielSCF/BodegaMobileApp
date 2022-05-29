package pe.idat.eduale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import pe.idat.eduale.adapter.OrderAdapter
import pe.idat.eduale.databinding.ActivityOrderPendientBinding
import pe.idat.eduale.model.OrderDetailModel
import pe.idat.eduale.network.OrderDetailService
import pe.idat.eduale.network.RetroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderPendientActivity : AppCompatActivity(), OnOrderListener {

    private lateinit var binding:ActivityOrderPendientBinding
    private lateinit var orderAdapter:OrderAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    var orderList = mutableListOf<OrderDetailModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderPendientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            sendUserData()
        }

        binding.btnHistory.setOnClickListener {
            sendUserDataHistory()
        }

        initItems()
    }

    private fun initItems(){
        orderAdapter = OrderAdapter(mutableListOf(), true,this@OrderPendientActivity )
        gridLayoutManager = GridLayoutManager(this, 1)

        getMyData()

        binding.recyclerOrder.apply {
            setHasFixedSize(true)
            adapter = orderAdapter
            layoutManager = gridLayoutManager
        }
    }

    private fun getMyData() {
        val objetoIntent: Intent = intent
        val ClienteID = objetoIntent.getStringExtra("ClienteID").toString().toInt()

        val retrofitData = RetroInstance().getRetroInstance().create(OrderDetailService::class.java)

        retrofitData.getOrderByClientIdAndState(ClienteID, "PENDIENTE").enqueue(object : Callback<List<OrderDetailModel>?> {
            override fun onResponse(
                call: Call<List<OrderDetailModel>?>,
                response: Response<List<OrderDetailModel>?>
            ) {
                val responseBody = response.body()

                val orders = responseBody ?: emptyList()

                orderList.addAll(orders)

                orderAdapter = OrderAdapter(orderList,true, this@OrderPendientActivity)
                orderAdapter.notifyDataSetChanged()
                binding.recyclerOrder.adapter = orderAdapter

            }
            override fun onFailure(call: Call<List<OrderDetailModel>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
            }

        })
    }

    override fun onOrderClick(position: Int) {
        TODO("Not yet implemented")
    }

    private fun sendUserData(){
        val value = Intent(this, ProductActivity::class.java)

        val objetoIntent: Intent = intent
        val ClienteID = objetoIntent.getStringExtra("ClienteID")
        val UsuarioID = objetoIntent.getStringExtra("UsuarioID")
        value.putExtra("ClienteID", ClienteID)
        value.putExtra("UsuarioID", UsuarioID)
        startActivity(value)
    }

    private fun sendUserDataHistory(){
        val value = Intent(this, OrderHistoryActivity::class.java)

        val objetoIntent: Intent = intent
        val ClienteID = objetoIntent.getStringExtra("ClienteID")
        val UsuarioID = objetoIntent.getStringExtra("UsuarioID")
        value.putExtra("ClienteID", ClienteID)
        value.putExtra("UsuarioID", UsuarioID)
        startActivity(value)
    }
}