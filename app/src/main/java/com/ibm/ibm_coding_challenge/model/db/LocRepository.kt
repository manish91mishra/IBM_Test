package com.ibm.ibm_coding_challenge.model.db

import android.content.Context
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow


class LocRepository(private val locDao: LocationDao, private val context: Context) {

    @WorkerThread
    suspend fun insertLocationsList(locations: ArrayList<LocationEntity>) {
        locDao.insertLocationList(locations)
    }

    @WorkerThread
    suspend fun insertLocation(location: LocationEntity) {
        locDao.insertLocation(location)
    }

    var allQueriesList: Flow<List<LocationEntity>> = locDao.getAllQueriesList()

}