package com.example.universityairlines.ui

import android.icu.text.SimpleDateFormat
import com.example.universityairlines.model.Airport
import java.util.*

     fun Long.toPrettyDate(): String? {
        val date = Date(this)
        val format = SimpleDateFormat.getDateInstance()
        return format.format(date)
}