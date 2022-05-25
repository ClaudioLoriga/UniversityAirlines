package com.example.universityairlines

import android.os.Bundle
import android.text.Layout
import androidx.appcompat.app.AppCompatActivity
import com.example.universityairlines.databinding.ActivityHomepageBinding
import com.example.universityairlines.databinding.LoginLayoutBinding

class HomepageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomepageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val stringName = intent.getStringExtra(EXTRAKEY)
        val stringHomePage = resources.getString(R.string.welcome_user, stringName)
        binding.textView.text = stringHomePage
    }

    companion object {
        const val EXTRAKEY = "username"
    }
}