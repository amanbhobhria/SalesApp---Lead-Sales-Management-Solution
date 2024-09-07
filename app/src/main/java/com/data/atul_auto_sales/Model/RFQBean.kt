package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class RFQBean(
    @SerializedName("data")
    val `data`: List<Any>,
    @SerializedName("error")
    val error: Boolean, // true
    @SerializedName("msg")
    val msg: String // File Not selected.
)