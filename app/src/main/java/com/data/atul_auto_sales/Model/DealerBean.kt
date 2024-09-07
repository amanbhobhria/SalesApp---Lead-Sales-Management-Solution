package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class DealerBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("address")
        val address: Any, // null
        @SerializedName("area_id")
        val areaId: Int, // 1
        @SerializedName("city")
        val city: Any, // null
        @SerializedName("company_name")
        val companyName: Any, // null
        @SerializedName("country")
        val country: Any, // null
        @SerializedName("created_at")
        val createdAt: String, // 2024-02-01 14:38:57
        @SerializedName("email")
        val email: String, // apitestdealer@gmail.com
        @SerializedName("fcm_token")
        val fcmToken: Any, // null
        @SerializedName("firebase_token")
        val firebaseToken: Any, // null
        @SerializedName("gst")
        val gst: Any, // null
        @SerializedName("id")
        val id: Int, // 41
        @SerializedName("is_active")
        val isActive: Int, // 1
        @SerializedName("land_mark")
        val landMark: Any, // null
        @SerializedName("last_ip")
        val lastIp: Any, // null
        @SerializedName("last_location")
        val lastLocation: Any, // null
        @SerializedName("last_login")
        val lastLogin: Any, // null
        @SerializedName("location_time")
        val locationTime: Any, // null
        @SerializedName("name")
        val name: String, // Api Test Dealer
        @SerializedName("pan_number")
        val panNumber: Any, // null
        @SerializedName("parent_id")
        val parentId: Int, // 27
        @SerializedName("password")
        val password: String, // 123456
        @SerializedName("phone")
        val phone: String, // 9696969696
        @SerializedName("pincode")
        val pincode: Any, // null
        @SerializedName("sm_id")
        val smId: Int, // 34
        @SerializedName("state")
        val state: Any, // null
        @SerializedName("token")
        val token: Any, // null
        @SerializedName("updated_at")
        val updatedAt: Any, // null
        @SerializedName("user_type")
        val userType: String, // Dealer
        @SerializedName("username")
        val username: String, // APi_test_delaer
        @SerializedName("whatsapp_number")
        val whatsappNumber: Any // null
    )
}