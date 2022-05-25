package pe.idat.eduale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import pe.idat.eduale.adapter.CartAdapter
import pe.idat.eduale.databinding.ActivityCartBinding
import pe.idat.eduale.model.ClientModel
import pe.idat.eduale.model.OrderDetailModel
import pe.idat.eduale.model.OrderModel
import pe.idat.eduale.model.ProductModel
import pe.idat.eduale.network.OrderDetailService
import pe.idat.eduale.network.OrderService
import pe.idat.eduale.network.RetroInstance
import pe.idat.eduale.room.cart.CartApp
import pe.idat.eduale.room.cart.CartModel
import pe.idat.eduale.room.cart.onItemListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class CartActivity : AppCompatActivity(), onItemListener {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var mGridLayout: GridLayoutManager
    var itemList = mutableListOf<CartModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            val objetoIntent: Intent = intent
            var ClienteID = objetoIntent.getStringExtra("ClienteID")
            var UsuarioID = objetoIntent.getStringExtra("UsuarioID")

            val value = Intent(this, ProductActivity::class.java)
            value.putExtra("ClienteID", ClienteID)
            value.putExtra("UsuarioID", UsuarioID)
            startActivity(value)
        }

        setCartRecyclerView()

        binding.btnRealizarPedido.setOnClickListener {
            registerOrder()
        }
    }

    private fun setCartRecyclerView() {
        cartAdapter = CartAdapter(mutableListOf(), this@CartActivity)
        mGridLayout = GridLayoutManager(this, 1)

        getCartList()
        setTotal()

        binding.recyclerCart.apply {
            setHasFixedSize(true)
            adapter = cartAdapter
            layoutManager = mGridLayout
        }
    }

    private fun getCartList() {
        doAsync {
            val items = CartApp.database.cartDao().cartList()
            uiThread {
                itemList.addAll(items)
                cartAdapter.setItems(itemList)
            }
        }
    }

    private fun setTotal() {
        doAsync {
            val items = CartApp.database.cartDao().cartList()
            val total = items.sumOf { it.subtotal }

            uiThread {
                binding.txtTotalValue.text = total.toString()
            }
        }
    }

    //Eliminar de la lista
    private fun deleteItem(cartModel: CartModel) {
        doAsync {
            CartApp.database.cartDao().deleteItem(cartModel)
            uiThread {
                itemList.remove(cartModel)
                cartAdapter.deleteItem(cartModel)
            }
        }
    }

    //Eliminar todos los elementos del carrito
    private fun clearCart(){
        doAsync {
            CartApp.database.cartDao().clearCart()
            uiThread {
                itemList.clear()
                cartAdapter.clearCart()
            }
        }
    }

    override fun onDeleteClick(position: Int) {
        val item = itemList.get(position)
        deleteItem(item)

        finish()
        overridePendingTransition(0, 0)

        val objetoIntent: Intent = intent
        var ClienteID = objetoIntent.getStringExtra("ClienteID")
        var UsuarioID = objetoIntent.getStringExtra("UsuarioID")

        val value = Intent(this, CartActivity::class.java)
        value.putExtra("ClienteID", ClienteID)
        value.putExtra("UsuarioID", UsuarioID)
        startActivity(value)

        overridePendingTransition(0, 0)
    }

    //Make order
    private fun registerOrder() {
        val objetoIntent: Intent = intent
        var ClienteID = objetoIntent.getStringExtra("ClienteID").toString().toInt()

        val total = itemList.sumOf { it.subtotal }

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())

        val request = OrderModel(
            pedidoID = null,
            fecha = currentDate.toString(),
            total = total,
            modalidad = "RECOJO",
            cliente = ClientModel(ClienteID, null, null, null, null, null),
            trabajador = null
        )

        val retro = RetroInstance().getRetroInstance().create(OrderService::class.java)
        retro.guardarpedido(request).enqueue(object : Callback<OrderModel> {
            override fun onResponse(call: Call<OrderModel>, response: Response<OrderModel>) {
                val user = response.body()

                for (item in itemList) {
                    val requests = OrderDetailModel(
                        pedido = OrderModel(
                            user?.pedidoID.toString().toInt(),
                            null,
                            null,
                            null,
                            null,
                            null
                        ),
                        cantidad = item.cantidad,
                        precio_venta = item.precio,
                        producto = ProductModel(
                            item.productId,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null
                        ),
                        subtotal = item.subtotal
                    )

                    val retro1 = RetroInstance().getRetroClientInstance()
                        .create(OrderDetailService::class.java)

                    retro1.registrarPedido(requests)
                        .enqueue(object : Callback<OrderDetailModel> {
                            override fun onResponse(
                                call: Call<OrderDetailModel>,
                                response: Response<OrderDetailModel>
                            ) {
                                if (response.isSuccessful) {
                                    itemList.clear()

                                    val objetoIntent: Intent = intent
                                    var ClienteID = objetoIntent.getStringExtra("ClienteID")
                                    var UsuarioID = objetoIntent.getStringExtra("UsuarioID")

                                    val value = Intent(this@CartActivity, ProductActivity::class.java)
                                    value.putExtra("ClienteID", ClienteID)
                                    value.putExtra("UsuarioID", UsuarioID)
                                    startActivity(value)


                                    Toast.makeText(
                                        this@CartActivity,
                                        "Pedido registrado",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            override fun onFailure(
                                call: Call<OrderDetailModel>,
                                t: Throwable
                            ) {
                                Toast.makeText(
                                    this@CartActivity,
                                    "Error al registrar pedido",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        })
                }
            }

            override fun onFailure(call: Call<OrderModel>, t: Throwable) {
                Toast.makeText(this@CartActivity, "Error al registrar pedido", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}