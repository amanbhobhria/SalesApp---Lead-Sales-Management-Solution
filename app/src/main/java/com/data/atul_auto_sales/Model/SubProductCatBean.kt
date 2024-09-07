package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class SubProductCatBean(
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
        @SerializedName("category_id")
        val categoryId: String, // 1
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("name")
        val name: String // SHOWER HINGES
    )
}