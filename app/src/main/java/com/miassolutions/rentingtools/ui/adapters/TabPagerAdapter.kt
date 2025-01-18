package com.miassolutions.rentingtools.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.miassolutions.rentingtools.ui.fragments.PaymentFragment
import com.miassolutions.rentingtools.ui.fragments.RentalFragment
import com.miassolutions.rentingtools.ui.fragments.ReturnFragment

class TabPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> RentalFragment()
            1-> ReturnFragment()
            2 -> PaymentFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}