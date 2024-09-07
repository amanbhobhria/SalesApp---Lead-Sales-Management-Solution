package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class ProjectCategoryBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("id")
        val id: Int, // 2
        @SerializedName("name")
        val name: String, // Showroom
        @SerializedName("type")
        val type: String // Commercial
    )
}