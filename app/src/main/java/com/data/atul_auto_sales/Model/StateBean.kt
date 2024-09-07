package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class StateBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("state")
        val state: String // Andaman Nicobar
    )
}