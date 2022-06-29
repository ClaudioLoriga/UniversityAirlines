package com.example.universityairlines.booking

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.universityairlines.R
import com.example.universityairlines.repository.UserRepository
import com.example.universityairlines.booking.adapter.AirportAdapter
import com.example.universityairlines.model.ApiResult
import com.example.universityairlines.model.GetAirportResponse
import kotlinx.coroutines.launch

class BookingAirportList : AppCompatActivity() {
    lateinit var textView: TextView
    lateinit var bottone: Button
    private lateinit var adapter: AirportAdapter

    //lateinit var listViewDemo: ListView
    lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_airport_list)
        recyclerview = findViewById(R.id.recycleviewairports)


        lifecycleScope.launch {
            val progressDialog = ProgressDialog(this@BookingAirportList)
            progressDialog.setTitle("Loading")
            progressDialog.show()

            val response = UserRepository.getAirports(
                "code",
                "name",
                "citycode",
                "city",
                "countrycode",
                "country",
                "continent"
            )

            progressDialog.hide()
            when (response) {
                is ApiResult.Success -> {
                    adapter = AirportAdapter(response.value.airports, ::getAirportName)
                    recyclerview.adapter = adapter
                    recyclerview.layoutManager = LinearLayoutManager(this@BookingAirportList)
                }
                is ApiResult.Failure -> Unit// Mappare errore
            }
        }

    }

    companion object {
        const val EXTRAKEY_AIRPORT = "Volo"
    }

    fun getAirportName(airportName: String) {
        val intent = Intent()
        intent.putExtra(EXTRAKEY_AIRPORT, airportName)
        setResult(RESULT_OK, intent)
        finish()
    }
}