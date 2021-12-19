package com.ibm.ibm_coding_challenge.viewmodels

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.IntentSender
import android.location.Location
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.*
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.ibm.ibm_coding_challenge.model.SearchResult
import com.ibm.ibm_coding_challenge.model.db.LocationEntity
import com.ibm.ibm_coding_challenge.model.network.WeatherApiService
import com.ibm.ibm_coding_challenge.ui.HomeActivity
import com.ibm.ibm_coding_challenge.utils.Constants
import com.ibm.ibm_coding_challenge.utils.Utils
import com.ibm.ibm_coding_challenge.utils.Utils.compositeDisposable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class LocationViewModel() : ViewModel() {
    val PERMISSION_REQUEST = 1000;

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    var location = MutableLiveData<Location>()
    var locationList=MutableLiveData<ArrayList<SearchResult>>()

    fun startLocationUpdates(activity: Activity) {
        if (Utils.checkPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
            locationRequest = LocationRequest.create().apply {
                interval = 10000
                fastestInterval = 5000
                priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
            }
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    if (locationResult.locations.size > 0)
                        location.value = locationResult.locations[0]
                }

                override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                    if (!locationAvailability.isLocationAvailable) {
                        val builder = LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest)
                        val client: SettingsClient = LocationServices.getSettingsClient(activity)
                        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

                        task.addOnFailureListener { exception ->
                            if (exception is ResolvableApiException) {
                                try {
                                    if (activity is HomeActivity) {
                                        activity.locationResultCallback.launch(IntentSenderRequest.Builder(exception.resolution).build())
                                    }
                                } catch (sendEx: IntentSender.SendIntentException) {
                                    Toast.makeText(activity, "Location not found", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        } else
            activity.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_REQUEST)

    }

    fun stopLocationUpdate() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    fun getLocations(isSearch: Boolean, searchText: String){
        val queries = mapOf(
            if (isSearch) Constants.QUERY to searchText else {
                val location=location.value!!
                Constants.LATLNG to "${location.latitude},${location.longitude}"
            }
        )

        compositeDisposable.add(
            WeatherApiService.getApi().getLocation(queries)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArrayList<SearchResult>>(){
                    override fun onSuccess(searchResults: ArrayList<SearchResult>) {
                        locationList.value=searchResults

                    }

                    override fun onError(e: Throwable) {

                    }
                })
        )
    }

    fun insertLocations(list:ArrayList<SearchResult>, application: Application){
        viewModelScope.launch {
            val entitiesList=Utils.getEntityListFromSearchResults(list)
            Utils.getRepo(application).insertLocationsList(entitiesList)
        }
    }

    fun insertQuery(query: String, application: Application){
        viewModelScope.launch {
            val entity=LocationEntity(query = query, timestamp = Utils.getTimeStanp())
            Utils.getRepo(application).insertLocation(entity)
        }
    }
}