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
import com.bring.chuchuba.adapter.RecyclerViewAdapter
import com.bring.chuchuba.adapter.base.BaseViewHolder
import com.bring.chuchuba.adapter.base.MyItem
import com.bring.chuchuba.databinding.FragmentHomeBinding
import com.bring.chuchuba.showToast
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeEvent
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeInjector
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeViewModel

import androidx.fragment.app.FragmentTransaction
import com.bring.chuchuba.adapter.PlaceRecyclerAdapter
import com.bring.chuchuba.model.mission.MissionsItem


class HomeFragment : Fragment() {

    private val TAG: String = "로그 ${this.javaClass.simpleName}"

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: PlaceRecyclerAdapter
    private lateinit var completedAdapter: PlaceRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            homeViewModel = ViewModelProvider(
                this,
                HomeInjector().provideViewModelFactory()
            ).get(
                HomeViewModel::class.java
            )
        }
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
        adapter = PlaceRecyclerAdapter()
        binding.homeRecyclerView.adapter = adapter
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(context)

        completedAdapter = PlaceRecyclerAdapter()
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
                missionList ?: return@Observer
                /**
                 * 완료된 미션과 그렇지 않은 미션으로 구분
                 */
                adapter.submitList(missionList.filter { it.status != "done" })
                completedAdapter.submitList(missionList.filter { it.status == "done" })
            }
        )
    }

    /**
     * 임시로 가족이름란에 "title, description, reward" 형식으로 보내면 등록
     * 새로운 프레그먼트를 띄우기
     */
    fun createMission() {

    }

}