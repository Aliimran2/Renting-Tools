package com.miassolutions.rentingtools.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.miassolutions.rentingtools.data.models.Tool

@Dao
interface ToolDao {

    //temporary later will be deleted TODO()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tools: List<Tool>)

    @Insert(onConflict = OnConflictStrategy.ABORT) // prevents duplicate entries
    suspend fun insertTool(tool: Tool)

    @Query("SELECT COUNT(*) FROM tools WHERE LOWER(toolName) = LOWER(:toolName)")
    suspend fun isToolExists(toolName: String) : Int

    @Update
    suspend fun updateTool(tool: Tool)

    @Query("SELECT * FROM tools WHERE LOWER(toolName) = LOWER(:toolName) LIMIT 1")
    suspend fun getToolByName(toolName: String): Tool?

    @Query("SELECT * FROM tools")
    fun getAllTools(): LiveData<List<Tool>>

    @Query("SELECT * FROM tools WHERE toolId =:toolId")
    fun getToolById(toolId: Long): LiveData<Tool?>

    @Query("SELECT * FROM tools WHERE toolName LIKE '%' || :query || '%'")
    fun searchToolsByName(query: String): LiveData<List<Tool>>
}