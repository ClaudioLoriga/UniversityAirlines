package com.example.universityairlines.booking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.universityairlines.booking.BookingFlightsListActivity.Companion.EXTRAKEY_ANDATA
import com.example.universityairlines.booking.BookingFlightsListActivity.Companion.EXTRAKEY_DESTINAZIONE
import com.example.universityairlines.booking.BookingFlightsListActivity.Companion.EXTRAKEY_ORIGINE
import com.example.universityairlines.booking.BookingFlightsListActivity.Companion.EXTRAKEY_PASSEGGERI
import com.example.universityairlines.booking.BookingFlightsListActivity.Companion.EXTRAKEY_RITORNO
import com.example.universityairlines.databinding.BookingFormLayoutBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.example.universityairlines.ui.toPrettyDate

class BookingActivity : AppCompatActivity() {

    private lateinit var binding: BookingFormLayoutBinding
    private val pickerAndata by lazy {
        MaterialDatePicker.Builder.datePicker()
            .setTitleText("Seleziona data")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BookingFormLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var number = 0

        binding.edittextorigine.setOnClickListener {
            val intent = Intent(this, BookingAirportList::class.java)
            startActivity(intent)
        }

        binding.edittextdestinazione.setOnClickListener {
            val intent = Intent(this, BookingAirportList::class.java)
            startActivity(intent)
        }

        binding.edittextandata.setOnClickListener {
            pickerAndata.clearOnPositiveButtonClickListeners()
            pickerAndata.addOnPositiveButtonClickListener {
                binding.edittextandata.setText(it.toPrettyDate())
            }
            pickerAndata.show(supportFragmentManager, null)
        }

        binding.edittextritorno.setOnClickListener {
            pickerAndata.clearOnPositiveButtonClickListeners()
            pickerAndata.addOnPositiveButtonClickListener {
                binding.edittextritorno.setText(it.toPrettyDate())
            }
            pickerAndata.show(supportFragmentManager, null)
        }

        binding.minusbutton.setOnClickListener {
            if (number > 1) {
                number = number.dec()
            }
            binding.npasseggeriedittext.setText(number.toString())
        }
        binding.plusbutton.setOnClickListener {
            number = number.inc()
            binding.npasseggeriedittext.setText(number.toString())
        }

        binding.cercavolibutton.setOnClickListener {
            val intent = Intent(this, BookingFlightsListActivity::class.java)
            intent.putExtra(EXTRAKEY_ORIGINE, binding.edittextorigine.text.toString())
            intent.putExtra(EXTRAKEY_DESTINAZIONE, binding.edittextdestinazione.text.toString())
            intent.putExtra(EXTRAKEY_ANDATA, binding.edittextandata.text.toString())
            intent.putExtra(EXTRAKEY_RITORNO, binding.edittextritorno.text.toString())
            intent.putExtra(EXTRAKEY_PASSEGGERI, binding.npasseggeriedittext.text.toString())
            startActivity(intent)
        }
    }

}

