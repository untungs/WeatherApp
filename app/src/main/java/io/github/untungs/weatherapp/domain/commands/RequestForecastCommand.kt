package io.github.untungs.weatherapp.domain.commands

import io.github.untungs.weatherapp.data.ForecastRequest
import io.github.untungs.weatherapp.domain.mappers.ForecastDataMapper
import io.github.untungs.weatherapp.domain.model.ForecastList

class RequestForecastCommand(val zipCode: String): Command<ForecastList> {
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
    }
}