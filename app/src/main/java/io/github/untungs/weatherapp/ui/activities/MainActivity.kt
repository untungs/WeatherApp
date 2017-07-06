package io.github.untungs.weatherapp.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import io.github.untungs.weatherapp.ui.adapters.ForecastListAdapter
import io.github.untungs.weatherapp.R
import io.github.untungs.weatherapp.domain.commands.RequestForecastCommand
import io.github.untungs.weatherapp.domain.model.ForecastList
import io.github.untungs.weatherapp.extensions.DelegatesExt
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), ToolbarManager {
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    val zipCode: Long by DelegatesExt.preference(this, SettingsActivity.ZIP_CODE,
            SettingsActivity.DEFAULT_ZIP_CODE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        toolbarTitle = getString(R.string.app_name)

        forecastList.layoutManager = LinearLayoutManager(this)
        forecastList.adapter = ForecastListAdapter(null) {}
        attachToScroll(forecastList)
    }

    override fun onResume() {
        super.onResume()
        loadForecast()
    }

    private fun loadForecast() = async(UI) {
        val result = bg { RequestForecastCommand(zipCode).execute() }
        updateUi(result.await())
    }

    private fun updateUi(weekForecast: ForecastList) {
        forecastList.adapter = ForecastListAdapter(weekForecast) {
            startActivity<DetailActivity>(
                    DetailActivity.ID to it.id,
                    DetailActivity.CITY_NAME to weekForecast.city)
        }
        toolbarTitle = "${weekForecast.city} (${weekForecast.country})"
    }
}
