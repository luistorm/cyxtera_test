package com.example.cyxteratest.data.models

import com.google.gson.annotations.SerializedName

data class TimeZoneResponse(
    @SerializedName("time")
    val time: String = ""
)