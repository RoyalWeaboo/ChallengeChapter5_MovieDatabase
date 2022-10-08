package com.malikazizali.challengechapter5.network

import com.malikazizali.challengechapter5.model.ResponseUserItem
import com.malikazizali.challengechapter5.model.User
import retrofit2.Call
import retrofit2.http.*

interface RestfulApiUser {
    @GET("user")
    fun getAllUser() : Call<List<ResponseUserItem>>

    @POST("user")
    fun addUser(@Body request : User) : Call<ResponseUserItem>

    @PUT("user/{id}")
    fun putUser(@Path("id") id:String, @Body request: User) : Call<ResponseUserItem>

}