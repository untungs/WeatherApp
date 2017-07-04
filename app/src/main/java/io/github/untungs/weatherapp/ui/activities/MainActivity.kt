package io.github.untungs.weatherapp.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import io.github.untungs.weatherapp.ui.adapters.ForecastListAdapter
import io.github.untungs.weatherapp.R
import io.github.untungs.weatherapp.domain.commands.RequestForecastCommand
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), ToolbarManager {
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        toolbarTitle = getString(R.string.app_name)

        forecastList.layoutManager = LinearLayoutManager(this)
        forecastList.adapter = ForecastListAdapter(null) {}
        attachToScroll(forecastList)

        doAsync {
            val result = RequestForecastCommand(94043).execute()
            uiThread {
                forecastList.adapter = ForecastListAdapter(result) {
                    startActivity<DetailActivity>(
                            DetailActivity.ID to it.id,
                            DetailActivity.CITY_NAME to result.city)
                }
            }
        }
    }
}
