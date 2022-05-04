package pe.idat.eduale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pe.idat.eduale.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.retroceder.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.loginButton.setOnClickListener{
            startActivity(Intent(this,ProductActivity::class.java))
        }
    }
}