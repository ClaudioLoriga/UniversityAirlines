package com.example.universityairlines.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.universityairlines.HomepageActivity
import com.example.universityairlines.R

class RegistrationSuccess : AppCompatActivity() {
    lateinit var bottoneHome: Button

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_success)
        bottoneHome = findViewById(R.id.bottoneHome)

        bottoneHome.setOnClickListener{
            val intent = Intent(this, HomepageActivity::class.java)
            startActivity(intent)
        }
    }
}