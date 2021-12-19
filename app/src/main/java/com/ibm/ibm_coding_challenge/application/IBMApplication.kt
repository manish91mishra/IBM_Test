package com.ibm.ibm_coding_challenge.application

import android.app.Application
import com.ibm.ibm_coding_challenge.model.db.LocRepository
import com.ibm.ibm_coding_challenge.model.db.LocRoomDatabase

class IBMApplication : Application(){
    private val database by lazy { LocRoomDatabase.getDatabase(this@IBMApplication) }
    val repository by lazy { LocRepository(database.userDao(),this@IBMApplication) }
}