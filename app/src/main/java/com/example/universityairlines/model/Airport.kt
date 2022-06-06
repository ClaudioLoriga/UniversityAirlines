package com.example.universityairlines.model


import com.fasterxml.jackson.annotation.JsonProperty

data class Airport(
    @JsonProperty("origin")
    val origin: String,
    @JsonProperty("destination")
    val destination: String,
    @JsonProperty("origin_iata")
    val originIata: String,
    @JsonProperty("destination_iata")
    val destinationIata: String,
    @JsonProperty("departure_date")
    val departureDate: String,
    @JsonProperty("return_date")
    val returnDate: String,
    @JsonProperty("price")
    val price: Int,
    @JsonProperty("currency")
    val currency: String
)