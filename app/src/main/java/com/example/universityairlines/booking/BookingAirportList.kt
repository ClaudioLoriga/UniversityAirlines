package com.example.universityairlines.booking

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.universityairlines.R
import com.example.universityairlines.UserRepository
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
        bottone = findViewById(R.id.idBottone)
        recyclerview = findViewById(R.id.recycleviewairports)

        bottone.setOnClickListener {
            lifecycleScope.launch {
                val response = UserRepository.getAirports(
                    "code",
                    "name",
                    "citycode",
                    "city",
                    "countrycode",
                    "country",
                    "continent"
                )

                when (response) {
                    is ApiResult.Success -> {
                        adapter = AirportAdapter(response.value.airports)
                        recyclerview.adapter = adapter
                        recyclerview.layoutManager = LinearLayoutManager(this@BookingAirportList)
                        creaListaAeroporti(response.value)
                    }
                    is ApiResult.Failure -> Unit// Mappare errore
                }
            }
        }
    }

    fun creaListaAeroporti(response: GetAirportResponse) {
        adapter.submitList(response.airports)
    }
}