package com.example.universityairlines

import com.example.universityairlines.model.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object UserRepository {

    private var retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl("https://universityairlines.altervista.org")
            .addConverterFactory(JacksonConverterFactory.create()).build()

    private val retrofitServiceLogin = retrofit.create(GetUserService::class.java)
    private val retrofitServiceRegistration = retrofit.create(SetUserService::class.java)
    private val retrofitServiceGetFlights = retrofit.create(GetFlightService::class.java)
    private val mapper = jacksonObjectMapper()

    suspend fun getUser(
        mail: String,
        password: String,
    ): ApiResult<LoginResponse> {
        val service: Call<LoginResponse> = retrofitServiceLogin.getUser(mail, password)
        return safeCall(service)
    }

    suspend fun setUser(
        mail: String, password: String, first_name: String, last_name: String
    ): ApiRegistrationResult<RegistrationResponse> {
        val service: Call<RegistrationResponse> =
            retrofitServiceRegistration.setUser(mail, password, first_name, last_name)
        return callRegistration(service)
    }

    suspend fun getFlights(
        origine: String,
        destinazione: String,
        dataDiPartenza: String,
        dataDiRitorno: String,
        numPasseggeri: String
    ): ApiResult<GetFlightsResponse> {
        val service: Call<GetFlightsResponse> =
            retrofitServiceGetFlights.getFlights(
                origine,
                destinazione,
                dataDiPartenza,
                dataDiRitorno,
                numPasseggeri
            )
        return safeCall(service)
    }

    private suspend fun <R : Any, T : Call<R>> safeCall(service: T): ApiResult<R> =
        withContext(Dispatchers.IO) {
            val response: Response<R> = service.awaitResponse()

            if (response.isSuccessful) {
                ApiResult.Success(response.body()!!)
            } else {
                val error = try {
                    mapper.readValue(response.errorBody()!!.string(), ErrorResponse::class.java)
                } catch (e: Exception) {
                    null
                }
                ApiResult.Failure(error)
            }
        }

    private suspend fun <R : Any, T : Call<R>> callRegistration(service: T): ApiRegistrationResult<R> =
        withContext(Dispatchers.IO) {
            val response: Response<R> = service.awaitResponse()

            if (response.isSuccessful) {
                ApiRegistrationResult.Success(response.body()!!)
            } else {
                ApiRegistrationResult.Failure(response.errorBody()!!)
            }
        }
}

// Datasource
interface GetUserService {
    @GET("/signin.php")
    fun getUser(
        @Query("mail") mail: String,
        @Query("password") password: String
    ): Call<LoginResponse>
}

interface SetUserService {
    @GET("/signup.php")
    fun setUser(
        @Query("mail") mail: String, @Query("password") password: String,
        @Query("first_name") first_name: String, @Query("last_name") last_name: String
    ): Call<RegistrationResponse>
}

interface GetFlightService {
    @GET("/get_flights.php")
    fun getFlights(
        @Query("origin") origine: String,
        @Query("destination") destinazione: String,
        @Query("departure_date") dataDiPartenza: String,
        @Query("return_date") dataDiRitorno: String,
        @Query("passenger_number") numPasseggeri: String
    ): Call<GetFlightsResponse>
}