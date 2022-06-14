package com.example.universityairlines.check_in

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.universityairlines.R
import com.example.universityairlines.databinding.ActivityCheckInBinding
import com.example.universityairlines.model.Flight
import com.example.universityairlines.ui.getString

class CheckInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val flightSelected = intent.extras?.getParcelable<Flight>("flight")
        //val pnr = intent.getStringExtra("pnr").orEmpty()

        /* if (flightSelected != null) {
             val (data, ora) = flightSelected.departureDate.split(" ")
             with(binding.buyedFlight) {
                 andataTextView.text = binding.getString(
                     R.string.airport_description,
                     flightSelected.origin,
                     flightSelected.originIata
                 )
                 ritornoTextView.text = binding.getString(
                     R.string.airport_description,
                     flightSelected.destination,
                     flightSelected.destinationIata
                 )
                 dataTextView.text =
                     binding.getString(R.string.booking_details_flight, "Data", data)
                 oraTextView.text =
                     binding.getString(R.string.booking_details_flight, "Ora", ora)
             }
         }

         */

        binding.effettuaCheckInButton.setOnClickListener {
            val intent = Intent(this, CheckInConfirmedActivity::class.java)
            //intent.putExtra("flight", flightSelected)
            //intent.putExtra("pnr", pnr)
            startActivity(intent)
        }
    }
}