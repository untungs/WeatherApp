package io.github.untungs.weatherapp.domain.datasource

import io.github.untungs.weatherapp.domain.model.Forecast
import io.github.untungs.weatherapp.domain.model.ForecastList

interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode: Long, startDate: Long, days: Int): ForecastList?
    fun requestDayForecast(id: Long): Forecast?
}