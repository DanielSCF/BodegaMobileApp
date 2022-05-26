package pe.idat.eduale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import pe.idat.eduale.databinding.ActivityClientEditBinding
import pe.idat.eduale.model.ClientModel
import pe.idat.eduale.network.ClientService
import pe.idat.eduale.network.RetroInstance
import retrofit2.Call
import retrofit2.Callback

class ClientEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initValues()

        binding.btnBack.setOnClickListener {
            sendAppData()
        }

        binding.btnEditarPerfil.setOnClickListener {
            editClient()
        }
    }

    //Inicializar valores de cajas de texto
    private fun initValues() {
        val objetoIntent: Intent = intent

        binding.txtNombrePerfil.setText(objetoIntent.getStringExtra("txtNombre"))
        binding.txtApellidoPerfil.setText(objetoIntent.getStringExtra("txtApellido"))
        binding.txtDireccionPerfil.setText(objetoIntent.getStringExtra("txtDireccion"))
        binding.txtTelefonoPerfil.setText(objetoIntent.getStringExtra("txtTelefono"))
        binding.txtDniPerfil.setText(objetoIntent.getStringExtra("txtDni"))
    }

    //Editar información de cliente
    private fun editClient() {
        val objetoIntent: Intent = intent
        val id = objetoIntent.getStringExtra("ClienteID")

        val request = ClientModel(
            apellidos = binding.txtApellidoPerfil.text.toString().trim(),
            clienteID = id.toString().toInt(),
            direccion = binding.txtDireccionPerfil.text.toString().trim(),
            nombre = binding.txtNombrePerfil.text.toString().trim(),
            dni = binding.txtDniPerfil.text.toString().trim(),
            telefono = binding.txtTelefonoPerfil.text.toString().trim()
        )

        val retro = RetroInstance().getRetroClientInstance().create(ClientService::class.java)
        retro.editarcliente(request).enqueue(object : Callback<ClientModel> {
            override fun onResponse(
                call: Call<ClientModel>,
                response: retrofit2.Response<ClientModel>
            ) {
                if (response.isSuccessful) {

                    Toast.makeText(
                        this@ClientEditActivity,
                        "Editado Correctamente",
                        Toast.LENGTH_LONG
                    ).show()
                    sendAppData()
                } else {
                    Toast.makeText(this@ClientEditActivity, "Error", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ClientModel>, t: Throwable) {
                Toast.makeText(
                    this@ClientEditActivity,
                    "Error",
                    Toast.LENGTH_LONG
                ).show()

                sendAppData()
            }
        })
    }

    //Recibir y enviar información
    private fun sendAppData(){
        val objetoIntent: Intent = intent
        val ClienteID = objetoIntent.getStringExtra("ClienteID")
        val UsuarioID = objetoIntent.getStringExtra("UsuarioID")

        val value = Intent(this, ClientInformationActivity::class.java)
        value.putExtra("ClienteID", ClienteID)
        value.putExtra("UsuarioID", UsuarioID)
        startActivity(value)
    }
}