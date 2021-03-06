package pe.idat.eduale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import pe.idat.eduale.databinding.ActivityLoginBinding
import pe.idat.eduale.model.UserLoginRequest
import pe.idat.eduale.model.UserResponse
import pe.idat.eduale.network.RetroInstance
import pe.idat.eduale.network.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            validateTextbox()
        }

        binding.btnRegisterAccount.setOnClickListener {
            startActivity(Intent(this, RegisterUserActivity::class.java))
        }

        binding.btnBack.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    //Validar si usuario y contraseña están vacíos
    private fun validateTextbox() {
        if (TextUtils.isEmpty(binding.usernameEditText.text.toString()) || TextUtils.isEmpty(binding.passwordEditText.text.toString())) {
            Toast.makeText(this, "Username Requerido", Toast.LENGTH_LONG).show()
        } else {
            login()
        }
    }

    //Validar existencia de usuario e iniciar sesión
    private fun login() {
        val request = UserLoginRequest()

        request.nickname = binding.usernameEditText.text.toString().trim()
        request.clave = binding.passwordEditText.text.toString().trim()

        val retro = RetroInstance().getRetroClientInstance()
            .create(UserService::class.java)
        retro.login(request).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val user = response.body()
                if (user?.data == "Bienvenido") {
                    Toast.makeText(this@LoginActivity, "Bienvenido", Toast.LENGTH_SHORT).show()

                    //Enviar la id de usuario y cliente a la página de inicio
                    val value = Intent(this@LoginActivity, ProductActivity::class.java)
                    value.putExtra("UsuarioID", user.usuario!!.usuarioID.toString())
                    value.putExtra("ClienteID", user.usuario.cliente.clienteID.toString())
                    startActivity(value)

                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Inicio de sesión fallido", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(
                    this@LoginActivity,
                    "Throwable" + t.localizedMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}