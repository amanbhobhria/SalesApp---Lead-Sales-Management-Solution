package com.data.atul_auto_sales.Model


import com.google.gson.annotations.SerializedName

data class AttendanceReportBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String // Record Loaded
) {
    data class Data(
        @SerializedName("date")
        val date: String, // 19-01-24
        @SerializedName("end_location")
        val endLocation: Any, // null
        @SerializedName("end_time")
        val endTime: String, // 10:45:11 pm
        @SerializedName("start_location")
        val startLocation: String, // 26.851412999999997,26.851412999999997
        @SerializedName("start_time")
        val startTime: String, // 10:45:05 pm
        @SerializedName("user_id")
        val userId: Int, // 20
        @SerializedName("worked_hours")
        val workedHours: String // 0 H. 0M.
    )
}