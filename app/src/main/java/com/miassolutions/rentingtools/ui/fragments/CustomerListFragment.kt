package com.miassolutions.rentingtools.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.miassolutions.rentingtools.R
import com.miassolutions.rentingtools.data.models.Customer
import com.miassolutions.rentingtools.databinding.FragmentCustomerListBinding
import com.miassolutions.rentingtools.myapp.MyApplication
import com.miassolutions.rentingtools.ui.adapters.CustomerListAdapter
import com.miassolutions.rentingtools.ui.viewmodel.SharedViewModel
import com.miassolutions.rentingtools.ui.viewmodel.SharedViewModelFactory
import com.miassolutions.rentingtools.utils.showToast

class CustomerListFragment : Fragment(R.layout.fragment_customer_list) {

    private var _binding: FragmentCustomerListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CustomerListAdapter

    private val rentalViewModel: SharedViewModel by activityViewModels {
        SharedViewModelFactory((requireActivity().application as MyApplication).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCustomerListBinding.bind(view)


        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        adapter = CustomerListAdapter(
            dialerClickListener = { initializePhoneCall(it.customerPhone) },
            navigationClickListener = { showToast(requireContext(), "Edit the customer") },
            navToDetailsClickListener = { customer ->
                navigateToCustomerManagerFragment(customer)

            }
        )
        binding.rvCustomerList.adapter = adapter
        binding.fabNewCustomer.setOnClickListener {
            findNavController().navigate(R.id.action_customerListFragment_to_addEditCustomerFragment)
        }
    }

    private fun observeViewModel() {
        rentalViewModel.allCustomers.observe(viewLifecycleOwner) {
            Log.d("CustomersListFragment", "Observed customers: $it")
            adapter.submitList(it)
        }
    }

    private fun navigateToCustomerManagerFragment(customer: Customer) {
        val customerId = customer.customerId
        val customerName = customer.customerName
        val action = CustomerListFragmentDirections.actionCustomerListFragmentToRentToolsFragment()
        findNavController().navigate(action)
    }

    private fun initializePhoneCall(phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")  // This opens the dialer with the number
            }
            startActivity(intent)
        } catch (e: Exception) {
            showToast(requireContext(), "Unable to open the dialer.")
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}