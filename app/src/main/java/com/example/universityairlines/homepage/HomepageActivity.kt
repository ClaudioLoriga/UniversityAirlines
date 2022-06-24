package com.example.universityairlines.homepage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.universityairlines.R
import com.example.universityairlines.booking.BookingActivity
import com.example.universityairlines.check_in.CheckInActivity
import com.example.universityairlines.databinding.ActivityHomepageBinding

class HomepageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomepageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val stringName = intent.getStringExtra(EXTRAKEY)
        val stringHomePage = resources.getString(R.string.welcome_user, stringName)
        binding.textView.text = stringHomePage

        binding.buttonPrenotazione.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java)
            startActivity(intent)
        }

        binding.buttonCerca.setOnClickListener {
            val intent = Intent(this, CheckInActivity::class.java)
            //intent.putExtra("flight", flightSelected)
            //intent.putExtra("pnr", pnr)
            startActivity(intent)
        }
    }

    companion object {
        const val EXTRAKEY = "username"
    }
}