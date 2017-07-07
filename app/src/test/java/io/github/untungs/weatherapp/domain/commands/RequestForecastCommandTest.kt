package io.github.untungs.weatherapp.domain.commands

import io.github.untungs.weatherapp.domain.datasource.ForecastProvider
import org.junit.Test
import org.mockito.Mockito.*

class RequestForecastCommandTest {
    @Test
    fun testProviderIsCalled() {
        val provider = mock(ForecastProvider::class.java)
        val command = RequestDayForecastCommand(0, provider)

        command.execute()

        verify(provider).requestForecast(0)
    }
}