package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class InsertClientBean(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String // RecordInserted.
) {
    data class Data(
        @SerializedName("active")
        val active: Int, // 1
        @SerializedName("address")
        val address: Any, // null
        @SerializedName("city")
        val city: String, // Zirakpur
        @SerializedName("created_At")
        val createdAt: String, // 2024-01-2017:46:46
        @SerializedName("doa")
        val doa: Any, // null
        @SerializedName("dob")
        val dob: Any, // null
        @SerializedName("email")
        val email: Any, // null
        @SerializedName("gst_no")
        val gstNo: Any, // null
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("name")
        val name: String, // testclient
        @SerializedName("number")
        val number: String, // 8529637412
        @SerializedName("state")
        val state: String, // Punjab
        @SerializedName("updated_at")
        val updatedAt: Any, // null
        @SerializedName("whatsapp_number")
        val whatsappNumber: Any // null
    )
}