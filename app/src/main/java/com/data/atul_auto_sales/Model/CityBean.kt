package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class CityBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("city")
        val city: String // Agra
    )
}