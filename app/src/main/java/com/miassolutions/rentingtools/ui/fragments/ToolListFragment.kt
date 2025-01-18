package com.miassolutions.rentingtools.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.miassolutions.rentingtools.R
import com.miassolutions.rentingtools.databinding.FragmentToolListBinding


class ToolListFragment : Fragment(R.layout.fragment_tool_list) {
    private var _binding : FragmentToolListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentToolListBinding.bind(view)


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}