package com.example.universityairlines.registration


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.universityairlines.HomepageActivity
import com.example.universityairlines.R
import com.example.universityairlines.UserRepository
import com.example.universityairlines.databinding.ActivityRegistrationBinding
import com.example.universityairlines.model.ApiRegistrationResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        binding.bottoneIscrizione.setOnClickListener { registrationUser() }

        //Torna indietro nell'activity del login
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

    }

    fun registrationUser() {
        lifecycleScope.launch {
            val mail = binding.emailInputRegistrazione.text.toString()
            val password = binding.passwordInputRegistrazione.text.toString()
            val nome = binding.nomeInputRegistrazione.text.toString()
            val cognome = binding.cognomeInputRegistrazione.text.toString()

            when (val result = UserRepository.setUser(mail, password, nome, cognome)) {
                is ApiRegistrationResult.Success -> {
                    // navigate to next screen with data
                    val intent = Intent(this@RegistrationActivity, RegistrationSuccessActivity::class.java)
                    intent.putExtra(RegistrationSuccessActivity.EXTRAKEY, nome)
                    startActivity(intent)
                }
                is ApiRegistrationResult.Failure<*> -> {
                    // show error
                    MaterialAlertDialogBuilder(this@RegistrationActivity)
                        .setTitle(resources.getString(R.string.attenzione))
                        .setMessage(
                            resources.getString(R.string.problema_riprovare)
                        ).show()
                }
            }
        }
    }
}