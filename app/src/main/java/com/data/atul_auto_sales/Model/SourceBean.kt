package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class SourceBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("created_date")
        val createdDate: String, // 2023-11-2115:29:30
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("name")
        val name: String // Architect
    )
}