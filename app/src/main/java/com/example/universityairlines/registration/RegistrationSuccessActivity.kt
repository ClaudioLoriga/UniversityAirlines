package com.example.universityairlines.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.universityairlines.HomepageActivity
import com.example.universityairlines.R
import com.example.universityairlines.databinding.ActivityRegistrationBinding
import com.example.universityairlines.databinding.ActivityRegistrationSuccessBinding

class RegistrationSuccessActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegistrationSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationSuccessBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val bottoneHome = binding.bottoneHome
        val stringNome = intent.getStringExtra(EXTRAKEY)

        bottoneHome.setOnClickListener{
            val intent = Intent(this, HomepageActivity::class.java)
            intent.putExtra(HomepageActivity.EXTRAKEY, stringNome)
            startActivity(intent)
        }
    }

    companion object {
        const val EXTRAKEY = "username"
    }
}