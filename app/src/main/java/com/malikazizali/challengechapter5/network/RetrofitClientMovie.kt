package com.malikazizali.challengechapter5.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientMovie {
    const val BASE_URL = "https://api.themoviedb.org/"

    val instance : RestfulApiMovie by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(RestfulApiMovie::class.java)
    }
}