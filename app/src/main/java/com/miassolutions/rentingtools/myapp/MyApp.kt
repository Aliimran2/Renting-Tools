package com.miassolutions.rentingtools.myapp

import android.app.Application
import com.miassolutions.rentingtools.core.AppDatabase
import com.miassolutions.rentingtools.data.ToolRentalRepository

class MyApp : Application() {

    private val database by lazy { AppDatabase.getDatabase(this) }

    val repository by lazy { ToolRentalRepository(database)
    }
}