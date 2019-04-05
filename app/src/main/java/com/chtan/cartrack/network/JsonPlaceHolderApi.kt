package com.chtan.cartrack.network

import retrofit2.http.GET
import retrofit2.Call;


interface JsonPlaceHolderApi {
    @GET("users")
    fun getUserDetail(): Call<List<RetroDetail>>
}