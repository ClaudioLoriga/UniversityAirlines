package com.example.universityairlines.login

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.edit
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.universityairlines.R
import com.example.universityairlines.homepage.HomepageActivity
import com.example.universityairlines.homepage.HomepageActivity.Companion.EXTRAKEY
import com.example.universityairlines.registration.RegistrationActivity
import com.example.universityairlines.repository.UserRepository
import com.example.universityairlines.databinding.LoginLayoutBinding
import com.example.universityairlines.model.ApiResult
import com.example.universityairlines.validation.setupValidation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    lateinit var bottoneRegistrazione: Button

    private lateinit var binding: LoginLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = LoginLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.loginbutton.setOnClickListener { checkUser() }

        binding.loginbutton.setOnClickListener {
            checkUser()
            it.isEnabled = false
        }

        //Gestione comportamento bottone registrazione
        bottoneRegistrazione = binding.registerbutton
        bottoneRegistrazione.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        with(binding) { setupValidation(loginbutton, edittextpassword, edittextemail) }
    }

    private fun checkUser() {
        lifecycleScope.launch {
            val mail = binding.edittextemail.text.toString()
            val pwd = binding.edittextpassword.text.toString()
            val progressDialog = ProgressDialog(this@LoginActivity)
            progressDialog.setTitle("Loading")
            progressDialog.show()

            when (val result = UserRepository.getUser(mail, pwd)) {
                is ApiResult.Success -> {
                    val intent = Intent(this@LoginActivity, HomepageActivity::class.java)
                    val sharedPref = getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE)
                    sharedPref.edit(commit = true) { putString(getString(R.string.username_shared_preferences), result.value.firstName) }
                    progressDialog.hide()
                    startActivity(intent)
                    finish()
                }
                is ApiResult.Failure -> {
                    MaterialAlertDialogBuilder(this@LoginActivity)
                        .setTitle("404 NOT FOUND")
                        .setPositiveButton(
                            "Ok"
                        ) { dialog, which -> dialog?.dismiss() }
                        .setMessage(
                            result.errorResponse?.message ?: "User Not Found"
                        ).show()
                    binding.loginbutton.isEnabled = true
                }
            }
        }
    }
}