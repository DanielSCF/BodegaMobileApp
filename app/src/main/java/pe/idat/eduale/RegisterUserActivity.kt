package pe.idat.eduale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import pe.idat.eduale.databinding.ActivityRegisterUserBinding
import pe.idat.eduale.model.ClientModel
import pe.idat.eduale.model.UserAccessModel
import pe.idat.eduale.model.UserRegisterModel
import pe.idat.eduale.network.RetroInstance
import pe.idat.eduale.network.UserRetroService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterUserActivity: AppCompatActivity() {

    private lateinit var binding : ActivityRegisterUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.retroceder.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

        initAction()
    }

    fun initAction(){
        binding.registrarseButton.setOnClickListener {

            if (TextUtils.isEmpty(binding.usernameEditText.text.toString()) || TextUtils.isEmpty(
                    binding.passwordEditText.text.toString())||
                TextUtils.isEmpty(binding.apellidoEditText.text.toString())|| TextUtils.isEmpty(binding.dniEditText.text.toString())
                || TextUtils.isEmpty(binding.nombresEditText.text.toString()) || TextUtils.isEmpty(binding.direccionEditText.text.toString())
                || TextUtils.isEmpty(binding.telefonoEditText.text.toString())
            )
            {
                Toast.makeText(this, "Todos los campos requeridos", Toast.LENGTH_LONG)
                    .show()
            } else {
                registro()
            }

        }
    }

    fun registro(){

        val request=UserRegisterModel(estado="A",nickname =binding.usernameEditText.text.toString().trim(),clave = binding.passwordEditText.text.toString().trim(),tipoAcceso =UserAccessModel(1),
            cliente= ClientModel(nombre = binding.nombresEditText.text.toString().trim(),apellidos = binding.apellidoEditText.text.toString(),dni = binding.dniEditText.text.toString().trim(),
                direccion = binding.dniEditText.text.toString().trim(),telefono = binding.telefonoEditText.text.toString().trim()))


        val retro= RetroInstance().getRetroClientInstance().create(UserRetroService::class.java)
        retro.guardarusuario(request).enqueue(object : Callback<UserRegisterModel> {

            override fun onResponse(call: Call<UserRegisterModel>, response: Response<UserRegisterModel>) {
                if(response.isSuccessful)
                {
                    Toast.makeText(this@RegisterUserActivity, "Registro Correcto", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@RegisterUserActivity,LoginActivity::class.java))

                }else{
                    Toast.makeText(this@RegisterUserActivity, "Registro Fallo", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<UserRegisterModel>, t: Throwable) {
                Toast.makeText(this@RegisterUserActivity, "Throwable"+t.localizedMessage, Toast.LENGTH_LONG).show()
            }

        })
    }

}