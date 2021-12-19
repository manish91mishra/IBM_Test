package com.ibm.ibm_coding_challenge.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.ibm.ibm_coding_challenge.utils.Constants.DB_TABLE_NAME
import java.util.*

@Entity(tableName = DB_TABLE_NAME)
data class LocationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val title: String = "",
    @ColumnInfo val woeid: String = "",
    @ColumnInfo val query: String = "",
    @ColumnInfo val latt_long: String = "",
    @ColumnInfo val timestamp: Date
)