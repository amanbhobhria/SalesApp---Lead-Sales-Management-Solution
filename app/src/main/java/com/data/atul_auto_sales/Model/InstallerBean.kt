package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class InstallerBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("address")
        val address: String, // una
        @SerializedName("city")
        val city: String, // Una
        @SerializedName("created_at")
        val createdAt: String, // 2023-11-21 15:34:46
        @SerializedName("doa")
        val doa: String, // 2020-01-01
        @SerializedName("dob")
        val dob: String, // 2000-01-01
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("name")
        val name: String, // Tabraz
        @SerializedName("name2")
        val name2: String, // Tabraz(9525027533)
        @SerializedName("number")
        val number: String, // 9525027533
        @SerializedName("state")
        val state: String, // HimachalPradesh
        @SerializedName("updated_at")
        val updatedAt: Any // null
    )
}