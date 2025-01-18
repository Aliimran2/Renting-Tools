package com.miassolutions.rentingtools.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.miassolutions.rentingtools.R
import com.miassolutions.rentingtools.databinding.FragmentReturnBinding

class ReturnFragment : Fragment(R.layout.fragment_return) {
    private var _binding : FragmentReturnBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReturnBinding.bind(view)


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}