/*package com.example.universityairlines.airportlist

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apitest.models.Airports
import com.example.universityairlines.R
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

class AirportList : AppCompatActivity() {
    var client = OkHttpClient()
    var request = OkHttpRequest(client)
    val url = "http://universityairlines.altervista.org/get_airports.php"
    lateinit var oggetto: ObjectMapper
    lateinit var textView: TextView
    lateinit var bottone: Button
    lateinit var bottoneLogin: Button
    lateinit var progress: ProgressDialog
    //val textView by lazy { findViewById<TextView>(R.id.idText) }

    //lateinit var listViewDemo: ListView
    lateinit var recyclerview: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        bottone  = findViewById(R.id.idBottone)

        oggetto = ObjectMapper()

        recyclerview = findViewById(R.id.recycleviewairports)


        bottone.setOnClickListener{
            progress = ProgressDialog(this)
            //  progress.show()

            request.apiGet(url, object: Callback {
                override fun onResponse(call: Call?, response: Response) {
                    val responseData = response.body()?.string()
                    val aeroporto: Airports = oggetto.readValue(responseData,Airports::class.java)



                    runOnUiThread{

                        if(aeroporto.airports != null){
                            val customAdapter = AirportAdapter(aeroporto.airports!!)
                            recyclerview.adapter = customAdapter
                            recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
                        }








                        //       progress.hide()
                        //val animali = arrayOf("giraffa", "leone", "gatto", "tigre", "cane", "pecora", "pesce")

/*
                        val lista = arrayListOf<String>()
                        aeroporto.airports?.forEach { airport ->
                          if (airport.name != null){
                            lista.add(airport.name!!)
                          }

                        }

                        val adapt: ArrayAdapter<String> = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1,lista)

                        //listViewDemo.adapter = adapt
                        textView.text = aeroporto.toString()
*/
                    }
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    runOnUiThread{
                        progress.hide()
                        textView.text = e.toString()
                    }

                }
            })
        }

    }






}



