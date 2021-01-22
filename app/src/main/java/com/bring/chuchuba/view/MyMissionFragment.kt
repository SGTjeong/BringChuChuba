package com.bring.chuchuba.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bring.chuchuba.BaseFragment
import com.bring.chuchuba.FragmentLayout
import com.bring.chuchuba.adapter.MyMissionFragmentAdapter
import com.bring.chuchuba.databinding.FragmentMyMissionBinding
import com.google.android.material.tabs.TabLayoutMediator

class MyMissionFragment(private val missionType : Int)
    : BaseFragment<FragmentMyMissionBinding>(FragmentLayout.MyMissionFragment) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
//        binding.infoFrag = this
//        binding.hvm = homeViewModel
//        observeViewModels()
        connectAdapter()
        return view
    }

    // 뷰 페이저와 프레그먼트,탭레이아웃 연결
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun connectAdapter() {
        val tabIconList: List<String> = when(missionType){
            0 -> listOf(
                "todo",
                "inProgress",
                "complete"
            )
            else -> listOf(
                "inProgress",
                "complete"
            )
        }

        binding.myMissionViewPager.adapter = MyMissionFragmentAdapter(requireActivity(), missionType = missionType)
        TabLayoutMediator(binding.myMissionTablayout, binding.myMissionViewPager) { tab, position ->
            tab.text = tabIconList[position]
        }.attach()
    }
}