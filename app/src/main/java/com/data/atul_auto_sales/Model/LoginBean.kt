package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class LoginBean(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String // User logged in successfully.
) {
    data class Data(
        @SerializedName("area_id")
        val areaId: Int, // 1
        @SerializedName("created_at")
        val createdAt: String, // 2024-01-12 10:34:58
        @SerializedName("email")
        val email: String, // salesmanager@gmail.com
        @SerializedName("fcm_token")
        val fcmToken: Any, // null
//        @SerializedName("firebase_token")
//        val firebaseToken: Any, // null
        @SerializedName("id")
        val id: Int, // 14
        @SerializedName("is_active")
        val isActive: Int, // 1
        @SerializedName("last_ip")
        val lastIp: Any, // null
        @SerializedName("last_location")
        val lastLocation: Any, // null
        @SerializedName("last_login")
        val lastLogin: String, // 2024-01-12 22:30:36
        @SerializedName("location_time")
        val locationTime: Any, // null
        @SerializedName("name")
        val name: String, // Test Developer
        @SerializedName("parent_id")
        val parentId: Int, // 9
        @SerializedName("password")
        val password: String, // 123456
        @SerializedName("phone")
        val phone: String, // 6666666666
        @SerializedName("sm_id")
        val smId: Any, // null
        @SerializedName("token")
        val token: String, // ETxHWLxTZEUI
        @SerializedName("updated_at")
        val updatedAt: String, // 2024-01-12 22:30:36
        @SerializedName("user_type")
        val userType: String, // Sales manager
        @SerializedName("username")
        val username: Any // null
    )
}