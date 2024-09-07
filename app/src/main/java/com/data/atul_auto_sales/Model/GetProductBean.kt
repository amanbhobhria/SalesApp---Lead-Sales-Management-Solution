package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class GetProductBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("barcode")
        val barcode: String,
        @SerializedName("category")
        val category: String, // SHOWERPARTITION
        @SerializedName("color")
        val color: String, // BLANK
        @SerializedName("created_at")
        val createdAt: String, // 0000-00-0000:00:00
        @SerializedName("description")
        val description: String,
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("img")
        val img: String,
        @SerializedName("is_active")
        val isActive: Int, // 0
        @SerializedName("min_stock")
        val minStock: String, // 0.00
        @SerializedName("name")
        val name: String, // Wall to Glass Hinge Two Way
        @SerializedName("price")
        val price: String, // 0.00
        @SerializedName("product_code")
        val productCode: String, // GSH-S-01
        @SerializedName("sub_category")
        val subCategory: String, // SHOWERHINGES
        @SerializedName("uom")
        val uom: String,
        @SerializedName("updated_at")
        val updatedAt: String, // 0000-00-00 00:00:00
        @SerializedName("warranty_days")
        val warrantyDays: Int // 0
    )
}