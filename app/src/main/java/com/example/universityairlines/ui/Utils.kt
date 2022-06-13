package com.example.universityairlines.ui

import android.icu.text.SimpleDateFormat
import androidx.annotation.StringRes
import com.example.universityairlines.databinding.ActivityBookingPaymentBinding
import com.example.universityairlines.databinding.BookingSimpleFlightViewBinding
import java.util.*

fun Long.toPrettyDate(): String? {
    val date = Date(this)
    val format = SimpleDateFormat("dd-MM-yyyy")
    return format.format(date)
}

fun BookingSimpleFlightViewBinding.getString(
    @StringRes id: Int,
    vararg params: String
) =
    root.context.resources.getString(id, *params)

fun ActivityBookingPaymentBinding.getString(
    @StringRes id: Int,
    vararg params: String
) =
    root.context.resources.getString(id, *params)


