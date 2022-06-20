package com.example.universityairlines.booking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.lifecycleScope
import com.example.universityairlines.R
import com.example.universityairlines.repository.UserRepository
import com.example.universityairlines.databinding.ActivityBookingPaymentConfirmationBinding
import com.example.universityairlines.homepage.HomepageActivity
import com.example.universityairlines.model.ApiResult
import com.example.universityairlines.model.Flight
import com.example.universityairlines.ui.getString
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class BookingPaymentConfirmationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingPaymentConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookingPaymentConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val flight = intent.extras?.getParcelable<Flight>("flight")
        val pnr = intent.getStringExtra(EXTRAKEY_PNR).orEmpty()
        val totalToPay = intent.getStringExtra(EXTRAKEY_TTP).orEmpty()

        if (flight != null) {
            val (data, ora) = flight.departureDate.split(" ")
            binding.prenotationCode.text = pnr

            with(binding.buyedFlight) {
                andataTextView.text = binding.getString(
                    R.string.airport_description,
                    flight.origin,
                    flight.originIata
                )
                ritornoTextView.text = binding.getString(
                    R.string.airport_description,
                    flight.destination,
                    flight.destinationIata
                )
                dataTextView.text =
                    binding.getString(R.string.booking_details_flight, "Data", data)
                oraTextView.text =
                    binding.getString(R.string.booking_details_flight, "Ora", ora)


                binding.totalPaid.text = binding.getString(
                    R.string.placeholder_price,
                    "",
                    totalToPay
                )
            }
        }

        binding.bottoneHome.setOnClickListener {
            val intent = Intent(this, HomepageActivity::class.java)
            intent.putExtra("flight", flight)
            intent.putExtra("pnr", binding.prenotationCode.text)
            startActivity(intent)
        }
    }

    companion object {
        const val EXTRAKEY_PNR = "pnr"
        const val EXTRAKEY_TTP = "totale_da_pagare"
        const val EXTRAKEY_CARD_NUMBER = "numero_carta"
        const val EXTRAKEY_CARD_EXPIRATION = "scadenza_carta"
        const val EXTRAKEY_CVV = "cvv_carta"
    }

}