package com.bring.chuchuba.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bring.chuchuba.view.CalendarFragment
import com.bring.chuchuba.view.HomeFragment

class CustomFragmentStateAdapter(fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity)
{
    /* 프래그먼트를 달아줄 어댑터 */

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> HomeFragment()
            else -> CalendarFragment()
        }
    }
}