package com.ibm.ibm_coding_challenge.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ibm.ibm_coding_challenge.model.SearchResult
import com.ibm.ibm_coding_challenge.model.WeatherDetailResponse
import com.ibm.ibm_coding_challenge.model.network.WeatherApiService
import com.ibm.ibm_coding_challenge.utils.Utils.compositeDisposable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherDetailsViewModel : ViewModel() {

    val weatherDetails = MutableLiveData<WeatherDetailResponse>()

    fun getWeatherData(woeid: String) {
        compositeDisposable.add(
            WeatherApiService.getApi().getWeatherDetails(woeid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherDetailResponse>() {
                    override fun onSuccess(response: WeatherDetailResponse) {
                        weatherDetails.value = response
                    }

                    override fun onError(e: Throwable) {

                    }
                })
        )
    }
}