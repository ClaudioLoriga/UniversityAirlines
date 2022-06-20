package com.example.universityairlines.booking

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.example.universityairlines.R
import com.example.universityairlines.repository.UserRepository
import com.example.universityairlines.databinding.ActivityBookingPaymentBinding
import com.example.universityairlines.model.ApiResult
import com.example.universityairlines.model.Flight
import com.example.universityairlines.model.Passenger
import com.example.universityairlines.ui.getString
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class BookingPaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val passengerDetails = intent.extras?.getParcelableArrayList<Passenger>("passengers")
        val flight = intent.extras?.getParcelable<Flight>("flightInfo")

        if (flight != null) {
            val (data, ora) = flight.departureDate.split(" ")
            with(binding.choosenFlight) {
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
                dataTextView.text = binding.getString(R.string.booking_details_flight, "Data", data)
                oraTextView.text = binding.getString(R.string.booking_details_flight, "Ora", ora)
            }

            if (passengerDetails != null) {
                with(binding) {
                    passengerNumberTextView.text =
                        binding.getString(
                            R.string.numero_passeggeri,
                            passengerDetails.size.toString()
                        )
                }

                lifecycleScope.launch {
                    val responseInit = UserRepository.getPaymentInit(
                        flight.origin,
                        flight.destination,
                        flight.departureDate,
                        flight.returnDate,
                        passengerDetails.size.toString(),
                        flight.price.toString()
                    )

                    when (responseInit) {
                        is ApiResult.Success -> {
                            binding.paySumTextView.text = binding.getString(
                                R.string.placeholder_price,
                                responseInit.value.totalToPay.toString(),
                                flight.currency
                            )
                        }
                        is ApiResult.Failure -> Unit// Mappare errore
                    }
                }


                binding.buttonPay.setOnClickListener {
                    lifecycleScope.launch {
                        val responseConfirmation = UserRepository.getPaymentConfirmation(
                            flight.origin,
                            flight.destination,
                            flight.departureDate,
                            flight.returnDate,
                            binding.paySumTextView.text.toString(),
                            binding.cardNumberEditText.text.toString(),
                            binding.expireEditText.text.toString(),
                            binding.cvvEditText.text.toString()
                        )

                        when (responseConfirmation) {
                            is ApiResult.Success -> {
                                val intent = Intent(
                                    this@BookingPaymentActivity,
                                    BookingPaymentConfirmationActivity::class.java
                                )
                                intent.putExtra("flight", flight)
                                intent.putExtra(
                                    BookingPaymentConfirmationActivity.EXTRAKEY_PNR,
                                    responseConfirmation.value.pnr
                                )
                                intent.putExtra(
                                    BookingPaymentConfirmationActivity.EXTRAKEY_TTP,
                                    binding.paySumTextView.text
                                )
                                intent.putExtra(
                                    BookingPaymentConfirmationActivity.EXTRAKEY_CARD_NUMBER,
                                    binding.cardNumberEditText.text.toString()
                                )
                                intent.putExtra(
                                    BookingPaymentConfirmationActivity.EXTRAKEY_CARD_EXPIRATION,
                                    binding.expireEditText.text.toString()
                                )
                                intent.putExtra(
                                    BookingPaymentConfirmationActivity.EXTRAKEY_CVV,
                                    binding.cvvEditText.text.toString()
                                )
                                startActivity(intent)
                            }

                            is ApiResult.Failure -> MaterialAlertDialogBuilder(this@BookingPaymentActivity)
                                .setTitle(resources.getString(R.string.attenzione))
                                .setMessage(
                                    resources.getString(R.string.problema_riprovare_pagamento)
                                ).show()
                        }
                    }
                }
            }
            binding.paySumTextView.text = binding.getString(
                R.string.placeholder_price,
                "",
                flight.price.toString()
            )
        }
    }
}


/*val (data, ora) = entry.departureDate.split(" ")
val stringAndata =
    binding.getString(R.string.airport_description, entry.origin, entry.originIata)
val stringRitorno = binding.getString(
    R.string.airport_description,
    entry.destination,
    entry.destinationIata
)
val stringData = binding.getString(R.string.booking_details_flight, "Data", data)
val stringOra = binding.getString(R.string.booking_details_flight, "Ora", ora)
with(binding) {
    andataTextView.text = stringAndata
    ritornoTextView.text = stringRitorno
    dataTextView.text = stringData
    oraTextView.text = stringOra

 */