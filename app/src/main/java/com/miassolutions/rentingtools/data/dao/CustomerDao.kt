package com.miassolutions.rentingtools.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.miassolutions.rentingtools.data.models.Customer

@Dao
interface CustomerDao {

    //later will be deleted TODO()
    @Insert
    suspend fun insertAllCustomers(customers: List<Customer>)

    @Insert(onConflict = OnConflictStrategy.ABORT) // Prevent duplicate entries
    suspend fun insertCustomer(customer: Customer)

    @Update
    suspend fun updateCustomer(customer: Customer)


    @Query("SELECT * FROM customers WHERE customerId = :customerId LIMIT 1")
    fun getCustomerById(customerId: Long): LiveData<Customer?>


    @Query("SELECT * FROM customers")
    fun getAllCustomers(): LiveData<List<Customer>>

    @Query("SELECT * FROM customers WHERE customerName LIKE '%' || :query || '%' OR customerPhone LIKE '%' || :query || '%'")
    fun searchCustomersByNameOrPhone(query: String): LiveData<List<Customer>>

}