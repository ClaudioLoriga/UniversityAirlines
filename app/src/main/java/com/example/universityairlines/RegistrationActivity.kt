package com.example.universityairlines


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class RegistrationActivity : AppCompatActivity() {
    lateinit var bottoneIscrizione: Button
    lateinit var edNome: EditText
    lateinit var edCognome: EditText
    lateinit var edEmail: EditText
    lateinit var edPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val actionBar = supportActionBar

        if(actionBar != null){

            actionBar.setDisplayHomeAsUpEnabled(true)

        }



        bottoneIscrizione = findViewById(R.id.bottoneIscrizione)
        edNome = findViewById(R.id.nomeInputRegistrazione)
        edCognome = findViewById(R.id.cognomeInputRegistrazione)
        edEmail = findViewById(R.id.emailInputRegistrazione)
        edPassword = findViewById(R.id.passwordInputRegistrazione)
    }
}