package pe.idat.eduale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import pe.idat.eduale.databinding.ActivityClientInformationBinding
import pe.idat.eduale.model.ClientModel
import pe.idat.eduale.network.ClientService
import pe.idat.eduale.network.RetroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClientInformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClientInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            val objetoIntent: Intent = intent
            var ClienteID = objetoIntent.getStringExtra("ClienteID")
            var UsuarioID = objetoIntent.getStringExtra("UsuarioID")

            var value = Intent(this, ProductActivity::class.java)
            value.putExtra("ClienteID", ClienteID)
            value.putExtra("UsuarioID", UsuarioID)
            startActivity(value)
        }

        buscarporid()
    }

    private fun buscarporid() {
        val objetoIntent: Intent = intent
        var ClienteID = objetoIntent.getStringExtra("ClienteID")

        val retro = RetroInstance().getRetroClientInstance().create(ClientService::class.java)
        retro.buscarcliente("$ClienteID".toInt()).enqueue(object : Callback<ClientModel> {
            override fun onResponse(call: Call<ClientModel>, response: Response<ClientModel>) {
                val user = response.body()

                if (response.isSuccessful) {
                    binding.txtname.text = user?.nombre.toString()
                    binding.txtsurname.text = response.body()?.apellidos.toString()
                    binding.txtadress.text = response.body()?.direccion.toString()
                    binding.txtphone.text = response.body()?.telefono.toString()
                    binding.txtdni.text = response.body()?.dni.toString()
                    binding.textView2.text = response.body()?.nombre.toString()

                    //envio de datos cuando es correcto y hacen click
                    binding.btnEditClient.setOnClickListener {
                        val value1 = Intent(this@ClientInformationActivity, ClientEditActivity::class.java)
                        value1.putExtra("ClienteID", "$ClienteID")
                        value1.putExtra("txtNombre", binding.txtname.text.toString().trim())
                        value1.putExtra("txtApellido", binding.txtsurname.text.toString().trim())
                        value1.putExtra("txtDireccion", binding.txtadress.text.toString().trim())
                        value1.putExtra("txtTelefono", binding.txtphone.text.toString().trim())
                        value1.putExtra("txtDni", binding.txtdni.text.toString().trim())
                        startActivity(value1)
                    }
                }

            }

            override fun onFailure(call: Call<ClientModel>, t: Throwable) {
                Toast.makeText(
                    this@ClientInformationActivity,
                    "Throwable" + t.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }

        })
    }
}