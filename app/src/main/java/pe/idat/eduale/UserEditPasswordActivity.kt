package pe.idat.eduale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import pe.idat.eduale.databinding.ActivityUserPasswordEditBinding
import pe.idat.eduale.model.UserModel
import pe.idat.eduale.network.RetroInstance
import pe.idat.eduale.network.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserEditPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserPasswordEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserPasswordEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findUser()

        binding.btnBack.setOnClickListener {
            sendUserData()
        }

        binding.btnEditarPerfil.setOnClickListener {
            editPassword()
        }
    }

    private fun findUser() {
        val objetoIntent: Intent = intent

        val UsuarioID = objetoIntent.getStringExtra("UsuarioID").toString().toInt()

        val retrofitData = RetroInstance().getRetroInstance().create(UserService::class.java)
        retrofitData.getUserById(UsuarioID).enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                val user = response.body()
                binding.txtUsername.text = user!!.nickname.replaceFirstChar { it.uppercase() }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Toast.makeText(this@UserEditPasswordActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun editPassword() {
        val password = binding.txtPassword.text.toString().trim()
        val confirmPassword = binding.txtConfirmPassword.text.toString().trim()

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show()
        } else if (password.compareTo(confirmPassword) !== 0) {
            Toast.makeText(this, "Los campos no son iguales", Toast.LENGTH_SHORT).show()
        } else {
            val objetoIntent: Intent = intent
            val UsuarioID = objetoIntent.getStringExtra("UsuarioID").toString().toInt()

            val retrofitData = RetroInstance().getRetroInstance().create(UserService::class.java)
            retrofitData.getUserById(UsuarioID).enqueue(object : Callback<UserModel> {
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                    val user = response.body()

                    val usuarioEditado = UserModel(
                        usuarioID = user!!.usuarioID,
                        nickname = user!!.nickname,
                        clave = binding.txtConfirmPassword.text.toString().trim(),
                        estado = user!!.estado,
                        trabajador = user!!.trabajador,
                        tipoAcceso = user!!.tipoAcceso,
                        cliente = user!!.cliente
                    )

                    val retro = RetroInstance().getRetroInstance().create(UserService::class.java)
                    retro.editUser(usuarioEditado).enqueue(object : Callback<UserModel> {
                        override fun onResponse(
                            call: Call<UserModel>,
                            response: Response<UserModel>
                        ) {
                            if (response.isSuccessful) {
                                Toast.makeText(this@UserEditPasswordActivity, "Contraseña actualizada", Toast.LENGTH_SHORT).show()
                                finish()
                                sendUserData()
                            }
                        }

                        override fun onFailure(call: Call<UserModel>, t: Throwable) {
                            Toast.makeText(
                                this@UserEditPasswordActivity,
                                "Error al actualizar contraseña",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    })
                }

                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    Toast.makeText(this@UserEditPasswordActivity, "Error", Toast.LENGTH_SHORT)
                        .show()
                }
            })

        }
    }

    private fun sendUserData(){
        val value = Intent(this, ProductActivity::class.java)

        val objetoIntent: Intent = intent
        val ClienteID = objetoIntent.getStringExtra("ClienteID")
        val UsuarioID = objetoIntent.getStringExtra("UsuarioID")
        value.putExtra("ClienteID", ClienteID)
        value.putExtra("UsuarioID", UsuarioID)
        startActivity(value)
    }

}