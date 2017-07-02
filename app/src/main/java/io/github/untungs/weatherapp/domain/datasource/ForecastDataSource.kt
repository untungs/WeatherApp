package io.github.untungs.weatherapp.domain.datasource

import io.github.untungs.weatherapp.domain.model.ForecastList

interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?
}