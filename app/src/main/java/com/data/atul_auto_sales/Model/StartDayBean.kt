package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class StartDayBean(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: Boolean, // true
    @SerializedName("msg")
    val msg: String // Dayalready Started for today.
) {
    data class Data(
        @SerializedName("post_data")
        val postData: PostData,
        @SerializedName("user_data")
        val userData: UserData
    ) {
        data class PostData(
            @SerializedName("last_location")
            val lastLocation: String // 17.23131,8556645
        )

        data class UserData(
            @SerializedName("area_id")
            val areaId: Int, // 1
            @SerializedName("created_at")
            val createdAt: String, // 2024-01-1210:34:58
            @SerializedName("email")
            val email: String, // salesmanager@gmail.com
            @SerializedName("fcm_token")
            val fcmToken: Any, // null
//            @SerializedName("firebase_token")
//            val firebaseToken: Any, // null
            @SerializedName("id")
            val id: Int, // 14
            @SerializedName("is_active")
            val isActive: Int, // 1
            @SerializedName("last_ip")
            val lastIp: Any, // null
            @SerializedName("last_location")
            val lastLocation: Any, // null
            @SerializedName("last_login")
            val lastLogin: String, // 2024-01-1911:18:39
            @SerializedName("location_time")
            val locationTime: Any, // null
            @SerializedName("name")
            val name: String, // TestDeveloper
            @SerializedName("parent_id")
            val parentId: Int, // 9
            @SerializedName("password")
            val password: String, // 123456
            @SerializedName("phone")
            val phone: String, // 6666666666
            @SerializedName("sm_id")
            val smId: Any, // null
            @SerializedName("token")
            val token: String, // Jh3ht1JPIUFc
            @SerializedName("updated_at")
            val updatedAt: String, // 2024-01-19 11:18:39
            @SerializedName("user_type")
            val userType: String, // Sales manager
            @SerializedName("username")
            val username: Any // null
        )
    }
}