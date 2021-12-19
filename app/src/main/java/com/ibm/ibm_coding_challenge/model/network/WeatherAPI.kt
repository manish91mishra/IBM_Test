package com.ibm.ibm_coding_challenge.model.network

import com.ibm.ibm_coding_challenge.model.SearchResult
import com.ibm.ibm_coding_challenge.model.WeatherDetailResponse
import com.ibm.ibm_coding_challenge.utils.Constants
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface WeatherAPI {
    @GET(Constants.ENDPOINT_SEARCH)
    fun getLocation(@QueryMap queries: Map<String, String>): Single<ArrayList<SearchResult>>

    @GET(Constants.ENDPOINT_LOCATION)
    fun getWeatherDetails(@Path(value = "woeid") woeid: String): Single<WeatherDetailResponse>
}