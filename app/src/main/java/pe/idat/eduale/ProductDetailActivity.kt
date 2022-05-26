package pe.idat.eduale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import pe.idat.eduale.adapter.CartAdapter
import pe.idat.eduale.databinding.ActivityProductDetailBinding
import pe.idat.eduale.room.cart.CartApp
import pe.idat.eduale.room.cart.CartModel
import pe.idat.eduale.room.cart.onItemListener

class ProductDetailActivity : AppCompatActivity(), onItemListener {

    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cartAdapter = CartAdapter(mutableListOf(), this@ProductDetailActivity)

        initValues()
        quantity()

        binding.btnMakeOrder.setOnClickListener {
            addToCart()
        }
        binding.btnBack.setOnClickListener {
            val objetoIntent: Intent = intent
            var ClienteID = objetoIntent.getStringExtra("ClienteID")
            var UsuarioID = objetoIntent.getStringExtra("UsuarioID")

            val value = Intent(this, ProductActivity::class.java)
            value.putExtra("ClienteID", ClienteID)
            value.putExtra("UsuarioID", UsuarioID)
            startActivity(value)
        }
    }

    //Inicializar valores de producto en la vista
    private fun initValues() {
        val objetoIntent: Intent = intent

        binding.txtPrecio.text = "S/. " + objetoIntent.getStringExtra("precio")
        binding.txtNombreProducto.text = objetoIntent.getStringExtra("nombreDesc")
        binding.txtStock.text = "Stock: " + objetoIntent.getStringExtra("stock")
        binding.txtCategoria.text = "Categoría:  " + objetoIntent.getStringExtra("categoria")
        binding.txtMarca.text = "Marca: " + objetoIntent.getStringExtra("marca")

        Glide.with(binding.ImgProduct.context).load(objetoIntent.getStringExtra("imagen"))
            .into(binding.ImgProduct)

        binding.txtCantidad.setText("1")

    }

    //Sumar o restar valores de cantidad
    private fun quantity() {
        var value = binding.txtCantidad.text.toString().toInt()
        binding.btnAdd.setOnClickListener {
            if (value > 0)
                value++
            binding.txtCantidad.setText(value.toString())
        }
        binding.btnMinus.setOnClickListener {
            if (value > 1)
                value--
            binding.txtCantidad.setText(value.toString())
        }
    }

    //Registrar producto y llevarlo al carrito
    private fun addToCart() {
        val objetoIntent: Intent = intent

        val cantidad = binding.txtCantidad.text.toString().toInt()
        val precio = objetoIntent.getStringExtra("precio").toString().toDouble()
        val subtotal = cantidad * precio

        val cart = CartModel(
            nombre = objetoIntent.getStringExtra("nombreDesc").toString(),
            precio = precio,
            cantidad = cantidad,
            imagen = objetoIntent.getStringExtra("imagen").toString(),
            subtotal = subtotal,
            productId = objetoIntent.getStringExtra("productoid").toString().toInt()
        )
        registerItem(cart)

        Toast.makeText(this, "Añadido al carrito", Toast.LENGTH_SHORT).show()
    }

    private fun registerItem(cartModel: CartModel) {
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