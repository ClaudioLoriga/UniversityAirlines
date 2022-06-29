package com.example.universityairlines.homepage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.universityairlines.R
import com.example.universityairlines.booking.BookingActivity
import com.example.universityairlines.check_in.CheckInActivity
import com.example.universityairlines.databinding.ActivityHomepageBinding
import com.example.universityairlines.validation.setupValidation

class HomepageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomepageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val sharedPref = getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE) ?: return
        val stringName = sharedPref.getString(getString(R.string.username_shared_preferences), "utente")
        val stringHomePage = resources.getString(R.string.welcome_user, stringName)

        binding.textView.text = stringHomePage

        binding.buttonPrenotazione.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java)
            startActivity(intent)
        }

        binding.buttonCerca.setOnClickListener {
            val code = binding.idCheckInInsert.text.toString()
            val intent = Intent(this, CheckInActivity::class.java)
            intent.putExtra("code", code)
            startActivity(intent)
        }

        with(binding) {
            setupValidation(buttonCerca, idCheckInInsert)
        }
    }

    companion object {
        const val EXTRAKEY = "username"
    }

}