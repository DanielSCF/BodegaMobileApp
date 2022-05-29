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
import pe.idat.eduale.room.cart.OnItemListener

class ProductDetailActivity : AppCompatActivity(), OnItemListener {

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
            val ClienteID = objetoIntent.getStringExtra("ClienteID")
            val UsuarioID = objetoIntent.getStringExtra("UsuarioID")

            val value = Intent(this, ProductActivity::class.java)
            value.putExtra("ClienteID", ClienteID)
            value.putExtra("UsuarioID", UsuarioID)
            startActivity(value)
        }

        binding.btnShoppingCart.setOnClickListener {
            val objetoIntent: Intent = intent
            val ClienteID = objetoIntent.getStringExtra("ClienteID")
            val UsuarioID = objetoIntent.getStringExtra("UsuarioID")

            val value = Intent(this, CartActivity::class.java)
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
        val stock = objetoIntent.getStringExtra("stock").toString().toInt()
        val subtotal = cantidad * precio

        val cart = CartModel(
            nombre = objetoIntent.getStringExtra("nombreDesc").toString(),
            marca = objetoIntent.getStringExtra("marca").toString(),
            precio = precio,
            cantidad = cantidad,
            stock = objetoIntent.getStringExtra("stock").toString().toInt(),
            imagen = objetoIntent.getStringExtra("imagen").toString(),
            subtotal = subtotal,
            productId = objetoIntent.getStringExtra("productoid").toString().toInt()
        )

        if(stock >= cantidad)
            registerItem(cart)
        else
            Toast.makeText(this, "Cantidad mayor a stock del producto", Toast.LENGTH_SHORT).show()

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

    override fun onAddClick(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onSubtractClick(position: Int) {
        TODO("Not yet implemented")
    }
}