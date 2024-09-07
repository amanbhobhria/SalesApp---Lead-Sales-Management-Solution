package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class ProfileColorBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("created_at")
        val createdAt: String, // 2024-01-25 17:04:59
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("name")
        val name: String // White
    )
}