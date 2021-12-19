package com.ibm.ibm_coding_challenge.model

import com.google.gson.annotations.SerializedName

data class WeatherDetailResponse(
    @SerializedName("consolidated_weather") val consolidated_weather:ArrayList<WeatherDetail>,
    @SerializedName("time") val time:String,
    @SerializedName("sun_rise") val sun_rise:String,
    @SerializedName("sun_set") val sun_set:String,
    @SerializedName("timezone_name") val timezone_name:String,
    @SerializedName("title") val title:String,
    @SerializedName("location_type") val location_type:String,
    @SerializedName("woeid") val woeid:String,
    @SerializedName("latt_long") val latt_long:String,
    @SerializedName("timezone") val timezone:String,
)