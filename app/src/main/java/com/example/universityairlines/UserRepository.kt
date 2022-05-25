package com.example.universityairlines

import com.example.universityairlines.model.ApiResult
import com.example.universityairlines.model.ErrorResponse
import com.example.universityairlines.model.LoginResponse
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
    private val mapper = jacksonObjectMapper()

    suspend fun getUser(
        mail: String,
        password: String,
    ): ApiResult<LoginResponse> {
        val service: Call<LoginResponse> = retrofitServiceLogin.getUser(mail, password)
        return call(service)
    }

    private suspend fun <R : Any, T : Call<R>> call(service: T): ApiResult<R> =
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
}

// Datasource
interface GetUserService {
    @GET("/signin.php")
    fun getUser(
        @Query("mail") mail: String,
        @Query("password") password: String
    ): Call<LoginResponse>
}