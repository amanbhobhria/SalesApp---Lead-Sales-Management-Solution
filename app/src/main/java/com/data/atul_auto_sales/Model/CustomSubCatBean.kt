package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class CustomSubCatBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("brand_id")
        val brandId: Int, // 1
        @SerializedName("created_at")
        val createdAt: String, // 0000-00-0000:00:00
        @SerializedName("custom_category_id")
        val customCategoryId: Int, // 1
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("name")
        val name: String // SHOWER HINGES
    )
}