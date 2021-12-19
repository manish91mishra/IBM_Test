package com.ibm.ibm_coding_challenge.model.network

import com.ibm.ibm_coding_challenge.utils.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object WeatherApiService {
    private var api: WeatherAPI = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    fun getApi(): WeatherAPI {
        return api
    }
}