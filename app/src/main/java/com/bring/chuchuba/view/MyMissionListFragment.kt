package com.bring.chuchuba.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bring.chuchuba.BaseFragment
import com.bring.chuchuba.FragmentLayout
import com.bring.chuchuba.R
import com.bring.chuchuba.adapter.mission.MissionListAdapter
import com.bring.chuchuba.databinding.FragmentMyMissionListBinding
import com.bring.chuchuba.model.mission.MissionsItem

class MyMissionListFragment(private val status: Int) :
    BaseFragment<FragmentMyMissionListBinding>(FragmentLayout.MyMissionListFragment) {

    private val TAG: String = "로그 ${this.javaClass.simpleName}"
    private lateinit var adapter: MissionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment
        observeViewModels()
        settingAdapter()
        return view
    }

    override fun observeViewModels() {
        homeViewModel.missionData.observe(viewLifecycleOwner) { missionList ->
            missionList ?: return@observe
            Log.d(TAG, "MyMissionListFragment ~ observeViewModels() called $missionList")
            when (status) {
                0 -> adapter.submitList(missionList.filter { it.status == "todo" })
                1 -> adapter.submitList(missionList.filter { it.status == "inProgress" })
                else -> adapter.submitList(missionList.filter { it.status == "complete" })
            }
        }
    }

    private fun settingAdapter() {
        adapter = MissionListAdapter(callback)
        binding.myMissionListRecyclerView.adapter = adapter
        binding.myMissionListRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    val callback: (MissionsItem) -> Unit = { mission ->
        val transaction =
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MissionDetailFragment(mission))
        transaction.addToBackStack(null)
        transaction.commit()
    }
}