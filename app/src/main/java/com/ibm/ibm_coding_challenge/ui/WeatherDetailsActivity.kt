package com.ibm.ibm_coding_challenge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.ibm.ibm_coding_challenge.R
import com.ibm.ibm_coding_challenge.databinding.ActivityWeatherDetailsBinding
import com.ibm.ibm_coding_challenge.model.WeatherDetail
import com.ibm.ibm_coding_challenge.ui.adapter.WeatherDetailsAdapter
import com.ibm.ibm_coding_challenge.utils.Constants
import com.ibm.ibm_coding_challenge.viewmodels.WeatherDetailsViewModel

class WeatherDetailsActivity : AppCompatActivity() {

    private lateinit var weatherDetailsViewModel: WeatherDetailsViewModel
    private lateinit var binding: ActivityWeatherDetailsBinding
    private var weatherList=ArrayList<WeatherDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        weatherDetailsViewModel = ViewModelProvider(this)[WeatherDetailsViewModel::class.java]
        binding = ActivityWeatherDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val woeid = intent.getStringExtra(Constants.WOEID)

        binding.recyclerWeatherDetails.apply {
            layoutManager=LinearLayoutManager(this@WeatherDetailsActivity,RecyclerView.VERTICAL,false)
            adapter=WeatherDetailsAdapter(this@WeatherDetailsActivity,weatherList)
        }

        weatherDetailsViewModel.weatherDetails.observe(this) { details ->
            binding.tvCity.text=details.title
            binding.tvType.text=details.location_type
            binding.tvTimezone.text=details.timezone

            weatherList.clear()
            weatherList.addAll(details.consolidated_weather)
            binding.recyclerWeatherDetails.adapter?.notifyDataSetChanged()
        }
        resources.getString(R.string.temperature,"34")
        woeid?.let { getWeatherData(it) }
    }

    private fun getWeatherData(woeid: String) {
        weatherDetailsViewModel.getWeatherData(woeid)
    }
}