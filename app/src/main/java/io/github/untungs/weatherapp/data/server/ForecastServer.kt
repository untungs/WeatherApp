package io.github.untungs.weatherapp.data.server

import io.github.untungs.weatherapp.data.db.ForecastDb
import io.github.untungs.weatherapp.domain.datasource.ForecastDataSource
import io.github.untungs.weatherapp.domain.model.ForecastList

class ForecastServer(
        val dataMapper: ServerDataMapper = ServerDataMapper(),
        val forecastDb: ForecastDb = ForecastDb()): ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val forecastResult = ForecastRequest(zipCode).execute()
        val forecastList = dataMapper.convertToDomain(forecastResult)
        forecastDb.saveForecast(forecastList)
        return forecastList
    }
}