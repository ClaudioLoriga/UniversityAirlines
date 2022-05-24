package com.example.universityairlines

import com.example.universityairlines.model.LoginResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.concurrent.thread

class OkHttpRequest(private val retrofit: Retrofit) {

    fun retroGET(mail: String, password: String, callback: (Response<LoginResponse>) -> Unit) {
        val service = retrofit.create(RetrofitServiceLogin::class.java)
        thread {
            val result: Response<LoginResponse> = service.getUser(mail, password).execute()
            callback.invoke(result)
        }
    }

    companion object {
        private val JSON = "application/json; charset = utf-8".toMediaTypeOrNull()
    }
}

interface RetrofitServiceLogin {
    @GET("http://universityairlines.altervista.org/signin.php")
    fun getUser(
        @Query("mail") mail: String,
        @Query("password") password: String
    ): retrofit2.Call<LoginResponse>
}