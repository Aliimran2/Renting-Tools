package com.miassolutions.rentingtools.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.whenCreated
import com.google.android.material.tabs.TabLayoutMediator
import com.miassolutions.rentingtools.R
import com.miassolutions.rentingtools.databinding.FragmentRentToolsBinding
import com.miassolutions.rentingtools.ui.adapters.TabPagerAdapter


class RentToolsFragment : Fragment(R.layout.fragment_rent_tools) {
    private var _binding: FragmentRentToolsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRentToolsBinding.bind(view)

        val adapter = TabPagerAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Rental"
                1 -> tab.text = "Return"
                2 -> tab.text = "Payment"
            }
        }.attach()


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}