package com.example.universityairlines.check_in

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.universityairlines.R
import com.example.universityairlines.databinding.ActivityCheckInConfirmedBinding

class CheckInConfirmedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckInConfirmedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckInConfirmedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.confirmedCheckIn.presentationTextView.text = getString(R.string.check_in_effettuato)
        binding.confirmedCheckIn.subPresentationTextView.text = getString(R.string.check_in_advise)
        binding.confirmedCheckIn.totalPaidPhrase.text = ""
        binding.confirmedCheckIn.voloAcquistatoPhrase.text = getString(R.string.volo)
    }
}