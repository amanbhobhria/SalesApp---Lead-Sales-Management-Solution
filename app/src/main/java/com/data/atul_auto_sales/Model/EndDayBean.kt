package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class EndDayBean(
    @SerializedName("data")
    val `data`: List<Any>,
    @SerializedName("error")
    val error: Boolean, // true
    @SerializedName("msg")
    val msg: String // Day already ended at 2024-01-19 22:45:11
)