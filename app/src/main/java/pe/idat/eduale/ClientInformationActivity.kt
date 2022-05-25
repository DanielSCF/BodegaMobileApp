package pe.idat.eduale

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pe.idat.eduale.databinding.ActivityClientInformationBinding

class ClientInformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClientInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            startActivity(Intent(this, ProductActivity::class.java))
        }

    }
}