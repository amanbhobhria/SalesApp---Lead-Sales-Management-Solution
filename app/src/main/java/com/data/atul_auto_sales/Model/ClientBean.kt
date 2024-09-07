package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class ClientBean(
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
        @SerializedName("address")
        val address: String, // pkl
        @SerializedName("city")
        val city: String, // Panchkula
        @SerializedName("created_At")
        val createdAt: String, // 2023-11-2116:03:45
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("name")
        val name: String, // Adesh
        @SerializedName("name2")
        val name2: String, // Adesh (8146653301)
        @SerializedName("number")
        val number: String, // 8146653301
        @SerializedName("state")
        val state: String, // Haryana
        @SerializedName("updated_at")
        val updatedAt: Any // null
    )
}