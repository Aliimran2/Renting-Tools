package com.miassolutions.rentingtools.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.miassolutions.rentingtools.data.dao.CustomerDao
import com.miassolutions.rentingtools.data.dao.ToolDao
import com.miassolutions.rentingtools.data.models.Customer
import com.miassolutions.rentingtools.data.models.Tool
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Tool::class, Customer::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun toolDao(): ToolDao
    abstract fun customerDao(): CustomerDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tool_rental_database"
                )
                    .fallbackToDestructiveMigration() // Reset database on schema changes
                    .addCallback(DatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(private val context: Context) : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                insertInitialData(context)
            }
        }


        // Method to insert initial data
        private fun insertInitialData(context: Context) {
            val toolDao = getDatabase(context).toolDao()
            val customerDao = getDatabase(context).customerDao()

            // List of tools to be inserted
            val tools = listOf(
                Tool(
                    toolName = "Hammer",
                    rentPerDay = 50.0,
                    stock = 10,
                    onRent = 0,
                    toolCondition = "New"
                ),
                Tool(
                    toolName = "Stands",
                    rentPerDay = 20.0,
                    stock = 20,
                    onRent = 0,
                    toolCondition = "New"
                ),
                Tool(
                    toolName = "Saw",
                    rentPerDay = 30.0,
                    stock = 30,
                    onRent = 0,
                    toolCondition = "Old"
                ),
                Tool(
                    toolName = "Drill",
                    rentPerDay = 40.0,
                    stock = 15,
                    onRent = 0,
                    toolCondition = "Old"
                ),
                Tool(
                    toolName = "Ladder",
                    rentPerDay = 100.0,
                    stock = 20,
                    onRent = 0,
                    toolCondition = "New"
                ),

                )

            // List of customers to be inserted
            val customers = listOf(
                Customer(
                    customerPic = "pic1.jpg",
                    customerName = "M Akram",
                    cnicNumber = "123456789",
                    customerPhone = "555-1234",
                    constructionPlace = "Site A",
                    contractorName = "Alice",
                    contractorPhone = "555-9876",
                    ownerName = "Bob",
                    ownerPhone = "555-1122"
                ),
                Customer(
                    customerPic = "pic2.jpg",
                    customerName = "Amjid Khan",
                    cnicNumber = "234567890",
                    customerPhone = "555-2345",
                    constructionPlace = "Site B",
                    contractorName = "Charlie",
                    contractorPhone = "555-8765",
                    ownerName = "Dave",
                    ownerPhone = "555-2233"
                ),
                Customer(
                    customerPic = "pic3.jpg",
                    customerName = "Hafiz Kareem",
                    cnicNumber = "345678901",
                    customerPhone = "555-3456",
                    constructionPlace = "Site C",
                    contractorName = "Eve",
                    contractorPhone = "555-7654",
                    ownerName = "Frank",
                    ownerPhone = "555-3344"
                ),
                Customer(
                    customerPic = "pic4.jpg",
                    customerName = "Boota",
                    cnicNumber = "456789012",
                    customerPhone = "555-4567",
                    constructionPlace = "Site D",
                    contractorName = "George",
                    contractorPhone = "555-6543",
                    ownerName = "Hannah",
                    ownerPhone = "555-4455"
                ),
                Customer(
                    customerPic = "pic5.jpg",
                    customerName = "Latif",
                    cnicNumber = "567890123",
                    customerPhone = "555-5678",
                    constructionPlace = "Site E",
                    contractorName = "Ivy",
                    contractorPhone = "555-5432",
                    ownerName = "Jack",
                    ownerPhone = "555-5566"
                )
            )

            CoroutineScope(Dispatchers.IO).launch {
                val db = getDatabase(context)
                db.toolDao().insertAll(tools)
                db.customerDao().insertAllCustomers(customers)
            }

        }

    }


}


