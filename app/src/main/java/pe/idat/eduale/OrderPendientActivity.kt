package pe.idat.eduale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import pe.idat.eduale.adapter.OrderAdapter
import pe.idat.eduale.adapter.OrderProductAdapter
import pe.idat.eduale.databinding.ActivityOrderPendientBinding
import pe.idat.eduale.model.ClientModel
import pe.idat.eduale.model.OrderDetailModel
import pe.idat.eduale.model.OrderModel
import pe.idat.eduale.network.ClientService
import pe.idat.eduale.network.OrderDetailService
import pe.idat.eduale.network.OrderService
import pe.idat.eduale.network.RetroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderPendientActivity : AppCompatActivity(), OnOrderListener {

    private lateinit var binding: ActivityOrderPendientBinding
    private lateinit var orderAdapter: OrderAdapter
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

    private fun initItems() {
        orderAdapter = OrderAdapter(mutableListOf(), true, this@OrderPendientActivity)
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

        retrofitData.getOrderByClientIdAndState(ClienteID, "PENDIENTE")
            .enqueue(object : Callback<List<OrderDetailModel>?> {
                override fun onResponse(
                    call: Call<List<OrderDetailModel>?>,
                    response: Response<List<OrderDetailModel>?>
                ) {
                    val responseBody = response.body()

                    val orders = responseBody ?: emptyList()

                    orderList.addAll(orders)

                    orderAdapter = OrderAdapter(orderList, true, this@OrderPendientActivity)
                    orderAdapter.notifyDataSetChanged()
                    binding.recyclerOrder.adapter = orderAdapter

                }

                override fun onFailure(call: Call<List<OrderDetailModel>?>, t: Throwable) {
                    Log.d("MainActivity", "onFailure: " + t.message)
                }

            })
    }

    override fun onOrderClick(position: Int) {
        val item = orderList.get(position)



        Toast.makeText(this, item.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onCancelOrderClick(position: Int) {
        val item = orderList.get(position)
        val objetoIntent: Intent = intent

        val ClienteID = objetoIntent.getStringExtra("ClienteID").toString().toInt()

        val retroCliente =
            RetroInstance().getRetroClientInstance().create(ClientService::class.java)
        retroCliente.buscarcliente(ClienteID).enqueue(object : Callback<ClientModel> {
            override fun onResponse(call: Call<ClientModel>, response: Response<ClientModel>) {
                val cliente = response.body()

                val cancelOrder = OrderModel(
                    pedidoID = item.pedido.pedidoID,
                    fecha = item.pedido.fecha,
                    total = item.pedido.total,
                    modalidad = item.pedido.modalidad,
                    estado = "CANCELADO",
                    trabajador = null,
                    cliente = cliente
                )

                val retroOrden = RetroInstance().getRetroInstance().create(OrderService::class.java)
                retroOrden.editarpedido(cancelOrder).enqueue(object : Callback<OrderModel> {
                    override fun onResponse(
                        call: Call<OrderModel>,
                        response: Response<OrderModel>
                    ) {
                        if(response.isSuccessful){
                            Toast.makeText(this@OrderPendientActivity, "Pedido cancelado", Toast.LENGTH_SHORT).show()
                            finish()
                            sendUserDataHistory()
                        }
                    }
                    override fun onFailure(call: Call<OrderModel>, t: Throwable) {
                        Toast.makeText(
                            this@OrderPendientActivity,
                            "Error al cancelar orden",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }

            override fun onFailure(call: Call<ClientModel>, t: Throwable) {
                Toast.makeText(
                    this@OrderPendientActivity,
                    "Error al cancelar orden",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun sendUserData() {
        val value = Intent(this, ProductActivity::class.java)

        val objetoIntent: Intent = intent
        val ClienteID = objetoIntent.getStringExtra("ClienteID")
        val UsuarioID = objetoIntent.getStringExtra("UsuarioID")
        value.putExtra("ClienteID", ClienteID)
        value.putExtra("UsuarioID", UsuarioID)
        startActivity(value)
    }

    private fun sendUserDataHistory() {
        val value = Intent(this, OrderHistoryActivity::class.java)

        val objetoIntent: Intent = intent
        val ClienteID = objetoIntent.getStringExtra("ClienteID")
        val UsuarioID = objetoIntent.getStringExtra("UsuarioID")
        value.putExtra("ClienteID", ClienteID)
        value.putExtra("UsuarioID", UsuarioID)
        startActivity(value)
    }
}