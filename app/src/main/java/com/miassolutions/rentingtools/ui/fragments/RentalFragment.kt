package com.miassolutions.rentingtools.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.miassolutions.rentingtools.R
import com.miassolutions.rentingtools.databinding.FragmentRentalBinding


class RentalFragment : Fragment(R.layout.fragment_rental) {

    private var _binding : FragmentRentalBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRentalBinding.bind(view)


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}