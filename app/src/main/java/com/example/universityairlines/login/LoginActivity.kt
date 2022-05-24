package com.example.universityairlines.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.universityairlines.HomepageActivity
import com.example.universityairlines.OkHttpRequest
import com.example.universityairlines.R
import com.example.universityairlines.databinding.ActivityMainBinding
import com.example.universityairlines.databinding.LoginLayoutBinding
import com.example.universityairlines.databinding.SplashLayoutBinding
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginLayoutBinding
    private var retrofit: Retrofit =
        Retrofit.Builder().addConverterFactory(JacksonConverterFactory.create()).build()
    private var okHttpRequest: OkHttpRequest = OkHttpRequest(retrofit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LoginLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.loginbutton.setOnClickListener { }

    }

    fun checkUser() {
        okHttpRequest.retroGET(binding.edittextemail.text.toString(), binding.edittextpassword.text.toString()) { loginResponse ->
            if (loginResponse.isSuccessful && loginResponse.body()?.status == "200") {
                val intent: Intent = Intent(this, HomepageActivity::class.java)
                startActivity(intent)
            } else {

            }

        }
    }


}