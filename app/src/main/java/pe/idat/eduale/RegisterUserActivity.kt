package pe.idat.eduale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pe.idat.eduale.databinding.ActivityRegisterUserBinding

class RegisterUserActivity: AppCompatActivity() {

    private lateinit var binding : ActivityRegisterUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.retroceder.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}