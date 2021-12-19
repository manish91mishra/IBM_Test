package com.ibm.ibm_coding_challenge.utils

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.net.ConnectivityManager
import com.ibm.ibm_coding_challenge.application.IBMApplication
import com.ibm.ibm_coding_challenge.model.SearchResult
import com.ibm.ibm_coding_challenge.model.db.LocRepository
import com.ibm.ibm_coding_challenge.model.db.LocationEntity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.*
import kotlin.collections.ArrayList

object Utils {
    val compositeDisposable = CompositeDisposable()

    fun checkPermission(context: Context, permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager?
        val network = manager?.activeNetwork

        return (network != null)
    }

    fun getTimeStanp(): Date {
        return Calendar.getInstance().time
    }

    fun getRepo(application: Application): LocRepository {
        return (application as IBMApplication).repository
    }

    suspend fun getEntityListFromSearchResults(list: ArrayList<SearchResult>): ArrayList<LocationEntity> {
        var entities = ArrayList<LocationEntity>()
        for (item in list) {
            entities.add(LocationEntity(title = item.title, woeid = item.woeid, latt_long = item.latt_long, timestamp = Utils.getTimeStanp()))
        }
        return entities
    }
}