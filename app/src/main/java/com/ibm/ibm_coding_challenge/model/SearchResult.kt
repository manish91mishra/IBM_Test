package com.ibm.ibm_coding_challenge.model

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("distance") val distance:String,
    @SerializedName("title") val title:String,
    @SerializedName("location_type") val location_type:String,
    @SerializedName("woeid") val woeid:String,
    @SerializedName("latt_long") val latt_long:String,
)
