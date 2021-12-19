package com.ibm.ibm_coding_challenge.model.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationList(locations: ArrayList<LocationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationEntity)

    @Query("SELECT * FROM TABLE_LOC")
    fun getAllQueriesList(): Flow<List<LocationEntity>>
}