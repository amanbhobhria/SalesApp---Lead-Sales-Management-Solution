package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class PropertyStageBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("active")
        val active: Int, // 1
        @SerializedName("created_at")
        val createdAt: String, // 2023-10-2716:32:59
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("title")
        val title: String, // Foundation 
        @SerializedName("updated_at")
        val updatedAt: Any // null
    )
}