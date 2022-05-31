package com.example.universityairlines

import com.example.universityairlines.model.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.http.GET
import retrofit2.http.Query

class UserRepository(retrofit: Retrofit) {

    private val retrofitServiceLogin = retrofit.create(GetUserService::class.java)
    private val retrofitServiceRegistration = retrofit.create(SetUserService::class.java)
    private val mapper = jacksonObjectMapper()

    suspend fun getUser(
        mail: String,
        password: String,
    ): ApiLoginResult<LoginResponse> {
        val service: Call<LoginResponse> = retrofitServiceLogin.getUser(mail, password)
        return callLogin(service)
    }

    suspend fun setUser(
        mail: String, password: String, first_name: String, last_name: String
    ): ApiRegistrationResult<RegistrationResponse> {
        val service: Call<RegistrationResponse> =
            retrofitServiceRegistration.setUser(mail, password, first_name, last_name)
        return callRegistration(service)
    }

    private suspend fun <R : Any, T : Call<R>> callLogin(service: T): ApiLoginResult<R> =
        withContext(Dispatchers.IO) {
            val response: Response<R> = service.awaitResponse()

            if (response.isSuccessful) {
                ApiLoginResult.Success(response.body()!!)
            } else {
                val error = try {
                    mapper.readValue(response.errorBody()!!.string(), ErrorResponse::class.java)
                } catch (e: Exception) {
                    null
                }
                ApiLoginResult.Failure(error)
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