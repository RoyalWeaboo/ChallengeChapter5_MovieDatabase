package com.malikazizali.challengechapter5.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.malikazizali.challengechapter5.model.ResponseMovie
import com.malikazizali.challengechapter5.model.ResponseUserItem
import com.malikazizali.challengechapter5.model.User
import com.malikazizali.challengechapter5.network.RetrofitClientMovie
import com.malikazizali.challengechapter5.network.RetrofitClientUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {

    var liveDataMovie: MutableLiveData<ResponseMovie>
    var liveDataUser: MutableLiveData<List<ResponseUserItem>>
    var liveDataAddUser : MutableLiveData<ResponseUserItem>
    var liveDataEditUser : MutableLiveData<ResponseUserItem>
    var loading = MutableLiveData<Boolean>()

    init {
        liveDataMovie = MutableLiveData()
        liveDataUser = MutableLiveData()
        liveDataAddUser = MutableLiveData()
        liveDataEditUser = MutableLiveData()
        callApiFilm()
    }

    fun getLDMovie(): MutableLiveData<ResponseMovie> {
        return liveDataMovie
    }

    fun getLDUser(): MutableLiveData<List<ResponseUserItem>> {
        return liveDataUser
    }

    fun getLDNewUser(): MutableLiveData<ResponseUserItem>{
        return liveDataAddUser
    }

    fun getLDUpdateUser(): MutableLiveData<ResponseUserItem>{
        return liveDataEditUser
    }

    fun callApiFilm() {
        loading.postValue(true)
        RetrofitClientMovie.instance.getAllMovie()
            .enqueue(object : Callback<ResponseMovie> {
                override fun onResponse(
                    call: Call<ResponseMovie>,
                    response: Response<ResponseMovie>
                ) {
                    if (response.isSuccessful) {
                        liveDataMovie.postValue(response.body())
                        Log.d("data", response.body()?.results.toString())
                    } else {
                        Log.d("data", response.body()?.results.toString())
                    }
                    loading.postValue(false)
                }

                override fun onFailure(call: Call<ResponseMovie>, t: Throwable) {
                    Log.d("data", call.toString())
                    loading.postValue(false)
                }

            })
    }

    fun callApiUser() {
        RetrofitClientUser.instance.getAllUser()
            .enqueue(object : Callback<List<ResponseUserItem>> {
                override fun onResponse(
                    call: Call<List<ResponseUserItem>>,
                    response: Response<List<ResponseUserItem>>
                ) {
                    if (response.isSuccessful) {
                        liveDataUser.postValue(response.body())
                        Log.d("data", response.body().toString())
                    } else {
                        Log.d("data", response.body().toString())
                    }
                    loading.postValue(false)
                }

                override fun onFailure(call: Call<List<ResponseUserItem>>, t: Throwable) {
                    Log.d("data", call.toString())
                    loading.postValue(true)
                }

            })
    }

    fun callApiAddUser(namaLengkap : String, username : String, pass : String) {
        loading.postValue(true)
        RetrofitClientUser.instance.addUser(User(namaLengkap, username, pass))
            .enqueue(object : Callback<ResponseUserItem> {
                override fun onResponse(
                    call: Call<ResponseUserItem>,
                    response: Response<ResponseUserItem>
                ) {
                    if (response.isSuccessful) {
                        liveDataAddUser.postValue(response.body())
                        Log.d("data", response.body().toString())
                    } else {
                        Log.d("data", response.body().toString())
                    }
                    loading.postValue(false)
                }

                override fun onFailure(call: Call<ResponseUserItem>, t: Throwable) {
                    Log.d("data", call.toString())
                    loading.postValue(true)
                }

            })
    }

    fun callApiEditUser(id : String, namaLengkap : String, username : String, pass : String) {
        RetrofitClientUser.instance.putUser(id, User(namaLengkap, username, pass))
            .enqueue(object : Callback<ResponseUserItem> {
                override fun onResponse(
                    call: Call<ResponseUserItem>,
                    response: Response<ResponseUserItem>
                ) {
                    if (response.isSuccessful) {
                        liveDataEditUser.postValue(response.body())
                        Log.d("data", response.body().toString())
                    } else {
                        Log.d("data", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<ResponseUserItem>, t: Throwable) {
                    Log.d("data", call.toString())
                }
            })
    }

}