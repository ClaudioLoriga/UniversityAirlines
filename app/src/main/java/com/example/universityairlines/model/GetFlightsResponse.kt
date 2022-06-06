package com.example.universityairlines.model


import com.fasterxml.jackson.annotation.JsonProperty

data class GetFlightsResponse(
    @JsonProperty("airports")
    val airports: List<Airport>,
    @JsonProperty("passengers_number")
    val passengersNumber: String
)