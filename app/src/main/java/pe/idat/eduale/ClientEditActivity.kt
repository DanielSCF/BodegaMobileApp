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

        initAction()

        binding.btnBack.setOnClickListener {
            val objetoIntent: Intent = intent
            var ClienteID = objetoIntent.getStringExtra("ClienteID")
            var UsuarioID = objetoIntent.getStringExtra("UsuarioID")

            val value = Intent(this, ClientInformationActivity::class.java)
            value.putExtra("ClienteID",ClienteID)
            value.putExtra("UsuarioID",UsuarioID)
            startActivity(value)
        }
    }

    fun initAction() {

        val objetoIntent: Intent = intent
        var Id = objetoIntent.getStringExtra("ClienteID")
        var Nombre = objetoIntent.getStringExtra("txtNombre")
        var Apellido = objetoIntent.getStringExtra("txtApellido")
        var Direccion = objetoIntent.getStringExtra("txtDireccion")
        var Telefono = objetoIntent.getStringExtra("txtTelefono")
        var Dni = objetoIntent.getStringExtra("txtDni")

        binding.txtNombrePerfil.setText(Nombre)
        binding.txtApellidoPerfil.setText(Apellido)
        binding.txtDireccionPerfil.setText(Direccion)
        binding.txtTelefonoPerfil.setText(Telefono)
        binding.txtDniPerfil.setText(Dni)


        binding.btnEditarPerfil.setOnClickListener {

            val request = ClientModel(
                apellidos = binding.txtApellidoPerfil.text.toString().trim(),
                clienteID = Id.toString().toInt(),
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

                        val value1 = Intent(this@ClientEditActivity, ClientInformationActivity::class.java)
                        value1.putExtra("ClienteID", "$Id")
                        startActivity(value1)

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

                    val value1 = Intent(this@ClientEditActivity, ClientInformationActivity::class.java)
                    value1.putExtra("ClienteID", "$Id")
                    startActivity(value1)
                }
            })
        }
    }
}