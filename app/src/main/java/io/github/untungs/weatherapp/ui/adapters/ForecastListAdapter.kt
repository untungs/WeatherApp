package io.github.untungs.weatherapp.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.github.untungs.weatherapp.R
import io.github.untungs.weatherapp.domain.model.Forecast
import io.github.untungs.weatherapp.domain.model.ForecastList
import io.github.untungs.weatherapp.extensions.toDateString
import io.github.untungs.weatherapp.extensions.ctx
import kotlinx.android.synthetic.main.item_forecast.view.*

class ForecastListAdapter(
        val weekForecast: ForecastList?,
        val itemClick: (Forecast) -> Unit): RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.ctx).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (weekForecast != null) holder?.bindForecast(weekForecast[position])
    }

    override fun getItemCount() = weekForecast?.size ?: 0

    class ViewHolder(view: View, val itemClick: (Forecast) -> Unit): RecyclerView.ViewHolder(view) {
        fun bindForecast(forecast: Forecast) {
            with(forecast) {
                Picasso.with(itemView.ctx).load(iconUrl).into(itemView.icon)
                itemView.date.text = date.toDateString()
                itemView.description.text = description
                itemView.maxTemperature.text = "$high"
                itemView.minTemperature.text = "$low"
                itemView.setOnClickListener {
                    itemClick(this)
                }
            }
        }
    }
}