package io.github.untungs.weatherapp.data.server

import io.github.untungs.weatherapp.data.db.ForecastDb
import io.github.untungs.weatherapp.domain.datasource.ForecastDataSource
import io.github.untungs.weatherapp.domain.model.ForecastList

class ForecastServer(
        val dataMapper: ServerDataMapper = ServerDataMapper(),
        val forecastDb: ForecastDb = ForecastDb()): ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, startDate: Long, days: Int): ForecastList? {
        val forecastResult = ForecastRequest(zipCode, days).execute()
        val forecastList = dataMapper.convertToDomain(zipCode, forecastResult)
        forecastDb.saveForecast(forecastList)
        return forecastDb.requestForecastByZipCode(zipCode, startDate, days)
    }

    override fun requestDayForecast(id: Long) = throw UnsupportedOperationException()
}