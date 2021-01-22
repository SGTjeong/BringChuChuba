package com.bring.chuchuba.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bring.chuchuba.view.MyMissionListFragment

class MyMissionFragmentAdapter (fragmentActivity: FragmentActivity)
    : FragmentStateAdapter(fragmentActivity)
{
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> MyMissionListFragment(status = 0)
            1 -> MyMissionListFragment(status = 1)
            else -> MyMissionListFragment(status = 2)
        }
    }
}