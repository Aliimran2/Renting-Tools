package com.miassolutions.rentingtools.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.miassolutions.rentingtools.R
import com.miassolutions.rentingtools.databinding.FragmentSummaryBinding


class SummaryFragment : Fragment(R.layout.fragment_summary) {

    private var _binding : FragmentSummaryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSummaryBinding.bind(view)


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}