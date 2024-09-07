package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class ProductCategoryBean(
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
        val createdAt: String, // 2023-11-1921:47:49
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("name")
        val name: String, // SHOWER PARTITION
        @SerializedName("updated_at")
        val updatedAt: Any // null
    )
}