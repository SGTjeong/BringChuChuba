package com.bring.chuchuba.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bring.chuchuba.view.MyMissionListFragment

class MyMissionFragmentAdapter(fragmentActivity: FragmentActivity, private val missionType: Int)
    : FragmentStateAdapter(fragmentActivity)
{
    override fun getItemCount(): Int {
        return if (missionType == 0) 3 else 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (missionType == 0) {
            when (position) {
                0 -> MyMissionListFragment(status = 0, missionType = missionType)
                1 -> MyMissionListFragment(status = 1, missionType = missionType)
                else -> MyMissionListFragment(status = 2, missionType = missionType)
            }
        }
        else {
            when (position) {
                0 -> MyMissionListFragment(status = 1, missionType = missionType)
                else -> MyMissionListFragment(status = 2, missionType = missionType)
            }
        }
    }
}