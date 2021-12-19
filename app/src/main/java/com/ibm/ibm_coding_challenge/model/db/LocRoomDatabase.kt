package com.ibm.ibm_coding_challenge.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ibm.ibm_coding_challenge.utils.Constants


@Database(entities = [LocationEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class LocRoomDatabase : RoomDatabase() {

    abstract fun userDao(): LocationDao

    companion object {

        @Volatile
        private var INSTANCE: LocRoomDatabase? = null

        fun getDatabase(context: Context): LocRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocRoomDatabase::class.java,
                    Constants.DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}