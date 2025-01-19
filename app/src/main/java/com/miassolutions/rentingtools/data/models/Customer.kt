package com.miassolutions.rentingtools.data.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "customers",
    indices = [Index(value = ["customerPhone"], unique = true)] // Prevent duplicate customers
)
data class Customer(
    @PrimaryKey(autoGenerate = true) val customerId: Long = 0L,
    var customerPic: String,
    var customerName: String,
    var cnicNumber: String,
    var customerPhone: String,
    var constructionPlace: String,
    var contractorName: String,
    var contractorPhone: String,
    var ownerName: String,
    var ownerPhone: String,
    var totalArrears : Double = 0.0
)

