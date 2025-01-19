package com.miassolutions.rentingtools.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miassolutions.rentingtools.data.ToolRentalRepository
import com.miassolutions.rentingtools.data.models.Customer
import com.miassolutions.rentingtools.data.models.Tool
import kotlinx.coroutines.launch

class SharedViewModel(private val repository: ToolRentalRepository) : ViewModel() {

    //fetching all tools
    val allTools: LiveData<List<Tool>> = repository.getAllTools()
    fun getToolById(toolId: Long): LiveData<Tool?> = repository.getToolById(toolId)
    fun searchToolByName(query: String): LiveData<List<Tool>> = repository.searchToolByName(query)

    private val _toolStatusMessage = MutableLiveData<String>()
    val toolStatusMessage: LiveData<String> get() = _toolStatusMessage

    fun insertTool(tool: Tool) {
        viewModelScope.launch {
            val result = repository.addToolIfNotExists(tool)
            if (result.isSuccess) {
                _toolStatusMessage.postValue("Tool inserted successfully in database")
            } else {
                _toolStatusMessage.postValue(
                    result.exceptionOrNull()?.message ?: "Error inserting the tool"
                )
            }
        }
    }

    //customer
    val allCustomers: LiveData<List<Customer>> = repository.getAllCustomers()



    fun getCustomerById(customerId: Long): LiveData<Customer?> =
        repository.getCustomerById(customerId)

    fun searchCustomerByNameOrPhone(query: String): LiveData<List<Customer>> =
        repository.searchCustomerByNameOrPhone(query)

    fun insertCustomer(customer: Customer) {
        viewModelScope.launch {
            repository.insertCustomer(customer)
        }
    }

    fun updateCustomer(customer: Customer) {
        viewModelScope.launch {
            repository.updateCustomer(customer)
            repository.getAllCustomers()


        }

    }


}


