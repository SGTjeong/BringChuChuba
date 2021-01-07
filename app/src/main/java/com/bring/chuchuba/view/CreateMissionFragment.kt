package com.bring.chuchuba.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bring.chuchuba.R
import com.bring.chuchuba.databinding.CreateMissionFragmentBinding
import com.bring.chuchuba.showToast
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeEvent
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeInjector
import com.bring.chuchuba.viewmodel.HomeViewModel

class CreateMissionFragment : Fragment() {

    private val TAG: String = "로그"

    var title = ""
    var description = ""
    var reward = ""
    var expireAt = ""
    private lateinit var binding: CreateMissionFragmentBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(
            requireActivity(),
            HomeInjector().provideViewModelFactory()
        ).get(
            HomeViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.create_mission_fragment, container, false)

        binding.cmFrag = this
        binding.lifecycleOwner = this

        return binding.root
    }

    fun submitMission(){
        if(title != "" && description != "" && reward != "" && expireAt != "") {
            homeViewModel.handleEvent(
                HomeEvent.OnCreateMission(
                    title, description, reward, expireAt
                )
            )
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        }
        else this.showToast("빈칸이 있습니다")

    }
}