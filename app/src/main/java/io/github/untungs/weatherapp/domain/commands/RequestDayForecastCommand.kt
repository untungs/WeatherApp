package io.github.untungs.weatherapp.domain.commands

import io.github.untungs.weatherapp.domain.datasource.ForecastProvider
import io.github.untungs.weatherapp.domain.model.Forecast

class RequestDayForecastCommand(
        val id: Long,
        val forecastProvider: ForecastProvider = ForecastProvider()): Command<Forecast> {

    override fun execute() = forecastProvider.requestForecast(id)
}