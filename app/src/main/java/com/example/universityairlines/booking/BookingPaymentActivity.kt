package com.example.universityairlines.booking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.universityairlines.R
import com.example.universityairlines.UserRepository
import com.example.universityairlines.booking.adapter.BookingFlightListAdapter
import com.example.universityairlines.databinding.ActivityBookingPaymentBinding
import com.example.universityairlines.model.ApiResult
import com.example.universityairlines.model.Flight
import com.example.universityairlines.model.FlightsResponse
import com.example.universityairlines.model.Passenger
import com.example.universityairlines.ui.getString
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
                    val response = UserRepository.getPaymentInit(
                        flight.origin,
                        flight.destination,
                        flight.departureDate,
                        flight.returnDate,
                        passengerDetails.size.toString(),
                        flight.price.toString()
                    )

                    when (response) {
                        is ApiResult.Success -> {
                            binding.paySumTextView.text = binding.getString(
                                R.string.placeholder_price,
                                response.value.totalToPay.toString(),
                                flight.currency
                            )
                        }
                        is ApiResult.Failure -> Unit// Mappare errore
                    }
                }
            }

            binding.paySumTextView.text = binding.getString(
                R.string.placeholder_price,
                flight.price.toString(),
                flight.currency
            )
        }

        binding.buttonPay.setOnClickListener { }
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