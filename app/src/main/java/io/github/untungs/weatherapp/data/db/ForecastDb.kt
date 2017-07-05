package io.github.untungs.weatherapp.data.db

import io.github.untungs.weatherapp.domain.datasource.ForecastDataSource
import io.github.untungs.weatherapp.domain.model.ForecastList
import io.github.untungs.weatherapp.extensions.clear
import io.github.untungs.weatherapp.extensions.parseList
import io.github.untungs.weatherapp.extensions.parseOpt
import io.github.untungs.weatherapp.extensions.toVarargArray
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class ForecastDb(
        val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
        val dbDataMapper: DbDataMapper = DbDataMapper()) : ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, startDate: Long, days: Int) = forecastDbHelper.use {
        val dailyRequest = "${DayForecastTable.CITY_ID} = ? AND ${DayForecastTable.DATE} >= ?"
        val dailyForecast = select(DayForecastTable.NAME)
                .whereSimple(dailyRequest, zipCode.toString(), startDate.toString())
                .limit(days)
                .parseList { DayForecast(HashMap(it)) }

        val city = select(CityForecastTable.NAME)
                .whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
                .parseOpt { CityForecast(HashMap(it), dailyForecast) }

        city?.let { dbDataMapper.convertToDomain(it) }
    }

    override fun requestDayForecast(id: Long) = forecastDbHelper.use {
        val dayForecast = select(DayForecastTable.NAME)
                .whereSimple("${DayForecastTable.ID} = ?", id.toString())
                .parseOpt { DayForecast(HashMap(it)) }

        dayForecast?.let { dbDataMapper.convertDayToDomain(it) }
    }

    fun saveForecast(forecastList: ForecastList) = forecastDbHelper.use {
        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)

        with(dbDataMapper.convertFromDomain(forecastList)) {
            insert(CityForecastTable.NAME, *map.toVarargArray())
            dailyForecast.forEach {
                insert(DayForecastTable.NAME, *it.map.toVarargArray())
            }
        }
    }
}