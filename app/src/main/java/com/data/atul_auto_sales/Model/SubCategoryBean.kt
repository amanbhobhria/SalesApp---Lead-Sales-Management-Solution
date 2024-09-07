package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class SubCategoryBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("catg_id")
        val catgId: Int, // 1
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("name")
        val name: String // 3 BHK
    )
}