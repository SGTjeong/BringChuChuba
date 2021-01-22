package com.bring.chuchuba.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bring.chuchuba.BaseFragment
import com.bring.chuchuba.FragmentLayout
import com.bring.chuchuba.databinding.FragmentMissionDetailBinding
import com.bring.chuchuba.model.mission.MissionsItem
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeEvent

class MissionDetailFragment(val mission: MissionsItem) : BaseFragment<FragmentMissionDetailBinding>(FragmentLayout.MissionDetailFragment) {
    private val TAG: String = "로그 ${this.javaClass.simpleName}"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        binding.mdFrag = this
        observeViewModels()
        restrictButton()
        return view
    }

    private fun restrictButton() {
        val myId = homeViewModel.myInfo.value?.id
        if (mission.client.id == myId)
            binding.contractMissionButton.visibility = View.GONE
        else
            binding.completeMissionButton.visibility = View.GONE
    }

    override fun observeViewModels() {


    }

    fun completeMission(){
        homeViewModel.handleEvent(HomeEvent.OnCompleteMission(mission))
    }

    fun contractMission(){
        homeViewModel.handleEvent(HomeEvent.OnContractMission(mission))
    }

}