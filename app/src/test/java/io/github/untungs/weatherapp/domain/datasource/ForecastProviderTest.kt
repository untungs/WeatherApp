package io.github.untungs.weatherapp.domain.datasource

import io.github.untungs.weatherapp.domain.model.Forecast
import io.github.untungs.weatherapp.domain.model.ForecastList
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.*

class ForecastProviderTest {
    @Test
    fun testDataSourceReturnNonNullValue() {
        val ds = mock(ForecastDataSource::class.java)
        `when`(ds.requestDayForecast(0)).then {
            Forecast(0, 0, "desc", 0, 0, "url")
        }

        val provider = ForecastProvider(listOf(ds))

        assertNotNull(provider.requestForecast(0))
    }

    @Test
    fun emptyDatabaseReturnServerValue() {
        val db = mock(ForecastDataSource::class.java)
        val server = mock(ForecastDataSource::class.java)
        `when`(server.requestForecastByZipCode(any(Long::class.java), any(Long::class.java),
                any(Int::class.java))).then {
            ForecastList(0, "city", "country", listOf())
        }

        val provider = ForecastProvider(listOf(db, server))

        assertNotNull(provider.requestByZipCode(0, 0))
    }
}