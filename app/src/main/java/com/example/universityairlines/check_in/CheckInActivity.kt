package com.example.universityairlines.check_in

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.universityairlines.R
import com.example.universityairlines.databinding.ActivityCheckInBinding
import com.example.universityairlines.model.Flight
import com.example.universityairlines.model.Reservation
import com.example.universityairlines.ui.getString
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class CheckInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckInBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val flightSelected = intent.extras?.getParcelable<Flight>("flight")
        val selectedCode = intent.getStringExtra("code").orEmpty()


        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = ""
        val reservationListString = sharedPref.getString(
            getString(R.string.reservation_list_shared_preferences),
            defaultValue
        )
        val mapper = jacksonObjectMapper()
        var reservationList = mapper.readValue(
            reservationListString,
            object : TypeReference<MutableList<Reservation>>() {})


        reservationList.forEach { reservation ->
            if (reservation.code == selectedCode) {

                with(binding.buyedFlight) {
                    /*andataTextView.text = binding.getString(
                        R.string.airport_description,
                        flight.origin,
                        flight.originIata
                    )*/
                    /*ritornoTextView.text = binding.getString(
                        R.string.airport_description,
                        flight.destination,
                        flight.destinationIata
                    )*/
                    dataTextView.text =
                        binding.getString(R.string.booking_details_flight, "Data", reservation.date)
                    oraTextView.text =
                        binding.getString(R.string.booking_details_flight, "Ora", reservation.hour)

                }
            }


        }



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