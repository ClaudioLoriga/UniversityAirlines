package com.example.universityairlines.booking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.TextView
import com.example.universityairlines.R
import com.example.universityairlines.databinding.BookingFormLayoutBinding

class BookingActivity : AppCompatActivity() {

    private lateinit var binding: BookingFormLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BookingFormLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        var number = 0

        binding.minusbutton.setOnClickListener {
            number = number.dec()
           binding.npasseggeriedittext.setText(number.toString())
        }
        binding.plusbutton.setOnClickListener {
            number = number.inc()
            binding.npasseggeriedittext.setText(number.toString())
        }

    }
}

