package com.bring.chuchuba.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bring.chuchuba.R
import com.bring.chuchuba.databinding.FragmentHomeBinding
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeInjector
import com.bring.chuchuba.viewmodel.HomeViewModel

import com.bring.chuchuba.adapter.mission.MissionListAdapter
import com.bring.chuchuba.showToast

class HomeFragment : Fragment() {

    private val TAG: String = "로그 ${this.javaClass.simpleName}"

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: MissionListAdapter
    private lateinit var completedAdapter: MissionListAdapter

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.homeVm = homeViewModel
        binding.homeFrag = this
        binding.lifecycleOwner = this

        settingAdapter()
        observeViewModels()

        return binding.root
    }

    private fun settingAdapter() {
        adapter = MissionListAdapter()
        binding.homeRecyclerView.adapter = adapter
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(context)

        completedAdapter = MissionListAdapter()
        binding.missionCompleteRecyclerView.adapter = completedAdapter
        binding.missionCompleteRecyclerView.layoutManager = LinearLayoutManager(context)
    }


    private fun observeViewModels() {
        homeViewModel.myInfo.observe(
            viewLifecycleOwner,
            Observer { member ->
                member ?: return@Observer
            }
        )

        homeViewModel.missionData.observe(
            viewLifecycleOwner,
            Observer { missionList ->
                Log.d(TAG, "HomeFragment ~ missiondata() called $missionList")
                missionList ?: return@Observer
                /**
                 * 완료된 미션과 그렇지 않은 미션으로 구분
                 */
                adapter.submitList(missionList.filter { it.status != "done" })
                completedAdapter.submitList(missionList.filter { it.status == "done" })
            }
        )
    }

    fun createMission() {
        val transaction =
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CreateMissionFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

}