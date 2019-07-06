package com.example.cyxteratest.data.network

import com.example.cyxteratest.data.models.TimeZoneResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("/timezoneJSON?formatted=true&username=qa_mobile_easy&style=full")
    fun getTimeZoneInfo(@Query("lat") deviceLatitude: Double, @Query("lng") deviceLongitude: Double): Observable<TimeZoneResponse>

}