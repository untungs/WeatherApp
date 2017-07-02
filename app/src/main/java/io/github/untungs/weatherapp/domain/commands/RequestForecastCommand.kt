package io.github.untungs.weatherapp.domain.commands

import io.github.untungs.weatherapp.data.server.ForecastRequest
import io.github.untungs.weatherapp.data.server.ServerDataMapper
import io.github.untungs.weatherapp.domain.datasource.ForecastProvider
import io.github.untungs.weatherapp.domain.model.ForecastList

class RequestForecastCommand(
        val zipCode: Long,
        val forecastProvider: ForecastProvider = ForecastProvider()): Command<ForecastList> {

    companion object {
        val DAYS = 7
    }

    override fun execute(): ForecastList {
        return forecastProvider.requestByZipCode(zipCode, DAYS)
    }
}