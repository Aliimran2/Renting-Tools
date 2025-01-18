package com.miassolutions.rentingtools

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.miassolutions.rentingtools.databinding.FragmentRentToolsBinding


class RentToolsFragment : Fragment(R.layout.fragment_rent_tools) {
    private var _binding : FragmentRentToolsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRentToolsBinding.bind(view)



    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}