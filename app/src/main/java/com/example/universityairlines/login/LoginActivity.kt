package com.example.universityairlines.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.example.universityairlines.HomepageActivity
import com.example.universityairlines.HomepageActivity.Companion.EXTRAKEY
import com.example.universityairlines.R
import com.example.universityairlines.registration.RegistrationActivity
import com.example.universityairlines.UserRepository
import com.example.universityairlines.databinding.LoginLayoutBinding
import com.example.universityairlines.model.ApiLoginResult
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class LoginActivity : AppCompatActivity() {

    lateinit var bottoneRegistrazione: Button

    private lateinit var binding: LoginLayoutBinding
    private var retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl("https://universityairlines.altervista.org")
            .addConverterFactory(JacksonConverterFactory.create()).build()
    private var userRepository: UserRepository = UserRepository(retrofit)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


       binding = LoginLayoutBinding.inflate(layoutInflater)
       val view = binding.root
       setContentView(view)

       binding.loginbutton.setOnClickListener { checkUser() }

    //Gestione comportamento bottone registrazione
        bottoneRegistrazione = findViewById(R.id.registerbutton)
        bottoneRegistrazione.setOnClickListener{
           val intent = Intent(this, RegistrationActivity::class.java)
           startActivity(intent)
       }

   }

   fun checkUser() {
       lifecycleScope.launch {
           val mail = binding.edittextemail.text.toString()
           val pwd = binding.edittextpassword.text.toString()

           when (val result = userRepository.getUser(mail, pwd)) {
               is ApiLoginResult.Success -> {
                   // navigate to next screen with data
                   val intent = Intent(this@LoginActivity, HomepageActivity::class.java)
                   intent.putExtra(EXTRAKEY, result.value.firstName)
                   startActivity(intent)
               }
               is ApiLoginResult.Failure -> {
                   // show error
                   MaterialAlertDialogBuilder(this@LoginActivity)
                       .setTitle("404 NOT FOUND")
                       .setMessage(
                           result.errorResponse?.message ?: "User Not Found"
                       ).show()
               }
           }
       }
   }


}