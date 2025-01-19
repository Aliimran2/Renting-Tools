package com.miassolutions.rentingtools.data.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tools",
    indices = [Index(value = ["toolName"], unique = true)] // Prevent duplicate tool names
)
data class Tool(
    @PrimaryKey(autoGenerate = true) val toolId: Long =0L,
    val toolName: String,
    var rentPerDay: Double,
    var stock: Int,
    var onRent: Int = 0,
    var toolCondition : String = "New"
)
