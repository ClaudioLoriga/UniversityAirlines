package com.example.universityairlines.model


import com.fasterxml.jackson.annotation.JsonProperty

data class Airport(
    val origin: String,
    val destination: String,
    @JsonProperty("origin_iata")
    val originIata: String,
    @JsonProperty("destination_iata")
    val destinationIata: String,
    @JsonProperty("departure_date")
    val departureDate: String,
    @JsonProperty("return_date")
    val returnDate: String,
    val price: Int,
    val currency: String
)