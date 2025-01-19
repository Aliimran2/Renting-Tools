package com.miassolutions.rentingtools.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miassolutions.rentingtools.core.AppDatabase
import com.miassolutions.rentingtools.data.models.Customer
import com.miassolutions.rentingtools.data.models.Tool


class ToolRentalRepository(private val db: AppDatabase) {

    private val toolDao = db.toolDao()
    private val customerDao = db.customerDao()

    fun getAllTools(): LiveData<List<Tool>> = toolDao.getAllTools()
    fun getToolById(toolId: Long): LiveData<Tool?> = toolDao.getToolById(toolId)
    fun searchToolByName(query: String): LiveData<List<Tool>> = toolDao.searchToolsByName(query)

    suspend fun addToolIfNotExists(tool: Tool): Result<Unit> {
        return try {
            val toolExist = toolDao.isToolExists(tool.toolName) > 0
            if (toolExist) {
                Result.failure(Exception("Tool with this name already exists"))
            } else {
                toolDao.insertTool(tool)
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getAllCustomers(): LiveData<List<Customer>> = customerDao.getAllCustomers()
    fun getCustomerById(customerId: Long): LiveData<Customer?> = customerDao.getCustomerById(customerId)

    suspend fun insertCustomer(customer: Customer) = customerDao.insertCustomer(customer)

    fun searchCustomerByNameOrPhone(query: String) : LiveData<List<Customer>> = customerDao.searchCustomersByNameOrPhone(query)


}
