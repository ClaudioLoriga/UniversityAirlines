package com.example.universityairlines.booking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.universityairlines.UserRepository
import com.example.universityairlines.booking.adapter.BookingFlightListAdapter
import com.example.universityairlines.databinding.BookingFlightsListBinding
import com.example.universityairlines.model.ApiResult
import com.example.universityairlines.model.GetFlightsResponse
import kotlinx.coroutines.launch
import retrofit2.Response


class BookingFlightsListActivity : AppCompatActivity() {

    private lateinit var binding: BookingFlightsListBinding
    private val adapter = BookingFlightListAdapter()
    private val layoutManager by lazy { LinearLayoutManager(this@BookingFlightsListActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = BookingFlightsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val stringOrigine = intent.getStringExtra(EXTRAKEY_ORIGINE).orEmpty()
        //val stringDestinazione = intent.getStringExtra(EXTRAKEY_DESTINAZIONE).orEmpty()
        val stringOrigine = "Cagliari"
        val stringDestinazione = "Bogota"
        val stringAndata = intent.getStringExtra(EXTRAKEY_ANDATA).orEmpty()
        val stringRitorno = intent.getStringExtra(EXTRAKEY_RITORNO).orEmpty()
        val stringPasseggeri = intent.getStringExtra(EXTRAKEY_PASSEGGERI).orEmpty()

        lifecycleScope.launch {

            val response = UserRepository.getFlights(
                stringOrigine,
                stringDestinazione,
                stringAndata,
                stringRitorno,
                stringPasseggeri
            )
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = layoutManager

            when (response) {
                is ApiResult.Success -> creaListaAeroporti(response.value)
                is ApiResult.Failure -> Unit// Mappare errore
            }


        }
    }

    companion object {
        const val EXTRAKEY_ORIGINE = "origine"
        const val EXTRAKEY_DESTINAZIONE = "destinazione"
        const val EXTRAKEY_ANDATA = "andata"
        const val EXTRAKEY_RITORNO = "ritorno"
        const val EXTRAKEY_PASSEGGERI = "passeggeri"
    }

    fun creaListaAeroporti(response: GetFlightsResponse) {
        adapter.submitList(response.airports)
    }
}