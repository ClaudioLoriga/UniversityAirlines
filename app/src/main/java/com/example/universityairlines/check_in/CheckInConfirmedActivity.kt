package com.example.universityairlines.check_in

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.universityairlines.R
import com.example.universityairlines.databinding.ActivityCheckInConfirmedBinding
import com.example.universityairlines.homepage.HomepageActivity
import com.example.universityairlines.model.Reservation
import com.example.universityairlines.ui.getString
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class CheckInConfirmedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckInConfirmedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckInConfirmedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var codeForCheckin = intent.getStringExtra("code").toString()
        binding.confirmedCheckIn.presentationTextView.text = getString(R.string.check_in_effettuato)
        binding.confirmedCheckIn.subPresentationTextView.text = getString(R.string.check_in_advise)
        binding.confirmedCheckIn.totalPaidPhrase.text = ""
        binding.confirmedCheckIn.voloAcquistatoPhrase.text = getString(R.string.volo)

        val sharedPref =
            getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE)
                ?: return
        val defaultValue = ""
        val reservationListString = sharedPref.getString(
            getString(R.string.reservation_list_shared_preferences),
            defaultValue
        )
        val mapper = jacksonObjectMapper()

        if (reservationListString != "") {
            var reservationList = mapper.readValue(
                reservationListString,
                object : TypeReference<MutableList<Reservation>>() {})

            reservationList.forEach { reservation ->
                if (reservation.code == codeForCheckin) {

                    with(binding.confirmedCheckIn.buyedFlight) {
                        andataTextView.text = binding.confirmedCheckIn.buyedFlight.getString(
                            com.example.universityairlines.R.string.booking_details_flight,
                            "Origine",
                            reservation.origin
                        )
                        ritornoTextView.text = binding.confirmedCheckIn.buyedFlight.getString(
                            com.example.universityairlines.R.string.booking_details_flight,
                            "Destinazione",
                            reservation.destination
                        )
                        dataTextView.text =
                            binding.confirmedCheckIn.buyedFlight.getString(
                                com.example.universityairlines.R.string.booking_details_flight,
                                "Data",
                                reservation.date
                            )
                        oraTextView.text =
                            binding.confirmedCheckIn.buyedFlight.getString(
                                com.example.universityairlines.R.string.booking_details_flight,
                                "Ora",
                                reservation.hour
                            )

                    }

                    binding.confirmedCheckIn.totalPaid.text = binding.confirmedCheckIn.getString(
                        R.string.placeholder_price,
                        "",
                        reservation.totalPrice
                    )

                    if (!reservation.checkin) {

                        var tempReservation = reservation
                        tempReservation.checkin = true
                        reservationList.remove(reservation)
                        reservationList.add(tempReservation)

                        with(sharedPref.edit()) {
                            putString(
                                getString(R.string.reservation_list_shared_preferences),
                                mapper.writeValueAsString(reservationList)
                            )
                            apply()
                        }
                    }
                }
            }
        }

        binding.confirmedCheckIn.bottoneHome.setOnClickListener {
            val intent = Intent(this, HomepageActivity::class.java)
            startActivity(intent)
        }
    }
}