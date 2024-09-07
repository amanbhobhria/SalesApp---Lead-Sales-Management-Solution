package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class GetAllStatusBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("day_status")
    val dayStatus: Int, // 1
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("status")
        val status: String // ADD LEAD
    )
}