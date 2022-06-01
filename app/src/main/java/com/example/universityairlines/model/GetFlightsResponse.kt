package com.example.universityairlines.model


import com.fasterxml.jackson.annotation.JsonProperty

data class GetFlightsResponse(
    val airports: List<Airport>,
    @JsonProperty("passengers_number")
    val passengersNumber: String
)