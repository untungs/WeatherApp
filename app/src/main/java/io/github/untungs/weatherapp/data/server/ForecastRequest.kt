package io.github.untungs.weatherapp.data.server

import com.google.gson.Gson
import java.net.URL

class ForecastRequest(zipCode: Long, days: Int, val gson: Gson = Gson()) {
    companion object {
        private val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private val URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric"
    }

    private val url: String

    init {
        url = "$URL&APPID=$APP_ID&q=$zipCode&cnt=$days"
    }

    fun execute(): ForecastResult {
        val forecastJsonStr = URL(url).readText()
        return gson.fromJson(forecastJsonStr, ForecastResult::class.java)
    }
}