package io.github.untungs.weatherapp.domain.datasource

import io.github.untungs.weatherapp.data.db.ForecastDb
import io.github.untungs.weatherapp.data.server.ForecastServer
import io.github.untungs.weatherapp.domain.model.Forecast
import io.github.untungs.weatherapp.domain.model.ForecastList
import io.github.untungs.weatherapp.extensions.firstResult

class ForecastProvider(val sources: List<ForecastDataSource> = ForecastProvider.SOURCES) {

    companion object {
        val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCES = listOf(ForecastDb(), ForecastServer())
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList = requestToSources {
        val forecastList = it.requestForecastByZipCode(zipCode, todayTimeSpan())
        if (forecastList != null && forecastList.size >= days) forecastList else null
    }

    fun requestForecast(id: Long): Forecast = requestToSources {
        it.requestDayForecast(id)
    }

    private fun <T : Any> requestToSources(f: (ForecastDataSource) -> T?): T =
            sources.firstResult { f(it) }

    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS
}