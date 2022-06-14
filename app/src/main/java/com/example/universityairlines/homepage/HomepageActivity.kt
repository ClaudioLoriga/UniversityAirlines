package com.example.universityairlines.homepage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.universityairlines.R
import com.example.universityairlines.booking.BookingActivity
import com.example.universityairlines.booking.BookingFlightsListActivity
import com.example.universityairlines.check_in.CheckInActivity
import com.example.universityairlines.databinding.ActivityHomepageBinding
import com.example.universityairlines.model.Flight

class HomepageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomepageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val stringName = intent.getStringExtra(EXTRAKEY)
        val stringHomePage = resources.getString(R.string.welcome_user, stringName)
        //val flightSelected = intent.extras?.getParcelable<Flight>("flight")
        //val pnr = intent.getStringExtra("pnr").orEmpty()
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