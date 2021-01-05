package com.bring.chuchuba.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bring.chuchuba.view.CalendarFragment
import com.bring.chuchuba.view.HomeFragment
import com.bring.chuchuba.view.MyInfoFragment

class CustomFragmentAdapter (fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity)
{
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> HomeFragment()
            1 -> CalendarFragment()
            else -> MyInfoFragment()
        }
    }
}