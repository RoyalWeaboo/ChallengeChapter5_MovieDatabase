package com.malikazizali.challengechapter5.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientUser {
    const val BASE_URL = "https://63403f57e44b83bc73ccaacc.mockapi.io/"

    val instance : RestfulApiUser by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(RestfulApiUser::class.java)
    }
}