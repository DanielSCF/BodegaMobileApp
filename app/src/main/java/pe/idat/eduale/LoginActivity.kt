package pe.idat.eduale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import pe.idat.eduale.databinding.ActivityLoginBinding
import pe.idat.eduale.model.UserLoginRequest
import pe.idat.eduale.model.UserLoginResponse
import pe.idat.eduale.network.RetroInstance
import pe.idat.eduale.network.UserRetroService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAction()
    }

    fun initAction(){
        binding.loginButton.setOnClickListener{

            if(TextUtils.isEmpty(binding.usernameEditText.text.toString()) || TextUtils.isEmpty(binding.passwordEditText.text.toString())) {
                Toast.makeText(this, "Username Requerido", Toast.LENGTH_LONG).show()
            } else {
                login()
            }
        }
    }

    fun login(){
        val request= UserLoginRequest()

        request.nickname = binding.usernameEditText.text.toString().trim()
        request.clave = binding.passwordEditText.text.toString().trim()

        val retro = RetroInstance().getRetroClientInstance()
            .create(UserRetroService::class.java)

        retro.login(request).enqueue(object : Callback<UserLoginResponse> {

            override fun onResponse(call: Call<UserLoginResponse>, response: Response<UserLoginResponse>) {

                val user=response.body()

                if(user?.data=="Bienvenido")
                {
                    Toast.makeText(this@LoginActivity, "Login Success",Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@LoginActivity,ProductActivity::class.java))

                }else{
                    Toast.makeText(this@LoginActivity, "Login Failure",Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Throwable"+t.localizedMessage,Toast.LENGTH_LONG).show()
            }
        })
    }

}