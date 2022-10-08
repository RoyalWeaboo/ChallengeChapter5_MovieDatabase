package com.malikazizali.challengechapter5.model


import com.google.gson.annotations.SerializedName

data class ResponseUserItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("namaLengkap")
    val namaLengkap: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)