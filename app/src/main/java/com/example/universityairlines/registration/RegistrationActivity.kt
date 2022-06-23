package com.example.universityairlines.registration


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.universityairlines.R
import com.example.universityairlines.repository.UserRepository
import com.example.universityairlines.databinding.ActivityRegistrationBinding
import com.example.universityairlines.model.ApiRegistrationResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)

        binding.bottoneIscrizione.setOnClickListener {

            registrationUser()

        }

        //Torna indietro nell'activity del login
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

    }

    fun registrationUser() {
        lifecycleScope.launch {
            val progressDialog = ProgressDialog(this@RegistrationActivity)
            val mail = binding.emailInputRegistrazione.text.toString()
            val password = binding.passwordInputRegistrazione.text.toString()
            val nome = binding.nomeInputRegistrazione.text.toString()
            val cognome = binding.cognomeInputRegistrazione.text.toString()

            progressDialog.setTitle("Loading")
            progressDialog.show()

            when (val result = UserRepository.setUser(mail, password, nome, cognome)) {
                is ApiRegistrationResult.Success -> {
                    // navigate to next screen with data
                    val intent = Intent(this@RegistrationActivity, RegistrationSuccessActivity::class.java)
                    intent.putExtra(RegistrationSuccessActivity.EXTRAKEY, nome)
                    progressDialog.hide()
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