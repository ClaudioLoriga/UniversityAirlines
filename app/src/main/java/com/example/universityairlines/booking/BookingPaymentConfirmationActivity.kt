package com.example.universityairlines.booking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.universityairlines.R
import com.example.universityairlines.UserRepository
import com.example.universityairlines.databinding.ActivityBookingPaymentConfirmationBinding
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
        val totalToPay = intent.getStringExtra(EXTRAKEY_TTP).orEmpty()
        val cardNumber = intent.getStringExtra(EXTRAKEY_CARD_NUMBER).orEmpty()
        val cardExpiration = intent.getStringExtra(EXTRAKEY_CARD_EXPIRATION).orEmpty()
        val cardCvv = intent.getStringExtra(EXTRAKEY_CVV).orEmpty()

        if (flight != null) {

            lifecycleScope.launch {
                val response = UserRepository.getPaymentConfirmation(
                    flight.origin,
                    flight.destination,
                    flight.origin,
                    flight.destination,
                    totalToPay,
                    cardNumber,
                    cardExpiration,
                    cardCvv
                )

                when (response) {
                    is ApiResult.Success -> {
                        val (data, ora) = flight.departureDate.split(" ")
                        binding.prenotationCode.text = response.value.pnr
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
                        }

                        binding.totalPaid.text = binding.getString(
                            R.string.placeholder_price,
                            totalToPay,
                            flight.currency
                        )
                    }

                    is ApiResult.Failure -> MaterialAlertDialogBuilder(this@BookingPaymentConfirmationActivity)
                        .setTitle(resources.getString(R.string.attenzione))
                        .setMessage(
                            resources.getString(R.string.problema_riprovare_pagamento)
                        ).show()
                }
            }
        }
    }

    companion object {
        const val EXTRAKEY_TTP = "totale_da_pagare"
        const val EXTRAKEY_CARD_NUMBER = "numero_carta"
        const val EXTRAKEY_CARD_EXPIRATION = "scadenza_carta"
        const val EXTRAKEY_CVV = "cvv_carta"
    }

}