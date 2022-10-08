package com.malikazizali.challengechapter5.network

import com.malikazizali.challengechapter5.model.ResponseMovie
import retrofit2.Call
import retrofit2.http.GET

interface RestfulApiMovie {
    @GET("3/movie/5/recommendations?api_key=3d256d2134e2623da20b963f3b2efcb6")
    fun getAllMovie() : Call<ResponseMovie>
}