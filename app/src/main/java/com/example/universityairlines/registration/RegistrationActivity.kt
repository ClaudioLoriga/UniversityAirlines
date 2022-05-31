package com.example.universityairlines.registration


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.universityairlines.UserRepository
import com.example.universityairlines.databinding.ActivityRegistrationBinding
import com.example.universityairlines.model.ApiRegistrationResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    private var retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl("https://universityairlines.altervista.org")
            .addConverterFactory(JacksonConverterFactory.create()).build()
    private var userRepository: UserRepository = UserRepository(retrofit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        binding.bottoneIscrizione.setOnClickListener { registrationUser() }

        //Torna indietro nell'activity del login
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

    }

    fun registrationUser() {
        lifecycleScope.launch {
            val mail = binding.emailInputRegistrazione.text.toString()
            val password = binding.passwordInputRegistrazione.text.toString()
            val nome = binding.nomeInputRegistrazione.text.toString()
            val cognome = binding.cognomeInputRegistrazione.text.toString()

            when (val result = userRepository.setUser(mail, password, nome, cognome)) {
                is ApiRegistrationResult.Success -> {
                    // navigate to next screen with data
                    val intent = Intent(this@RegistrationActivity, RegistrationSuccess::class.java)
                    //intent.putExtra(HomepageActivity.EXTRAKEY, result.value.firstName)
                    startActivity(intent)
                }
                is ApiRegistrationResult.Failure<*> -> {
                    // show error
                    MaterialAlertDialogBuilder(this@RegistrationActivity)
                        .setTitle("Attenzione")
                        .setMessage(
                            "C'è stato un problema durante la registrazione.\nSi prega di riprovare."
                        ).show()
                }
            }
        }
    }
}