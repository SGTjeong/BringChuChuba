package com.bring.chuchuba.view

import android.os.Bundle
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

class MyMissionListFragment(private val status: Int, private val missionType: Int) :
    BaseFragment<FragmentMyMissionListBinding>(FragmentLayout.MyMissionListFragment) {

    private val TAG: String = "로그 ${this.javaClass.simpleName}"
    private lateinit var adapter: MissionListAdapter
    private var myId: String? = null
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
        myId = homeViewModel.myInfo.value?.id
        homeViewModel.missionData.observe(viewLifecycleOwner) { missionList ->
            missionList ?: return@observe
            myId ?: return@observe
            if (missionType == 0)
                when (status) {
                    0 -> adapter.submitList(missionList.filter { it.client.id == myId && it.status == "todo" })
                    1 -> adapter.submitList(missionList.filter { it.client.id == myId && it.status == "inProgress" })
                    else -> adapter.submitList(missionList.filter { it.client.id == myId && it.status == "complete" })
                }
            else
                when (status) {
                    1 -> adapter.submitList(missionList.filter { it.contractor?.id == myId && it.status == "inProgress" })
                    else -> adapter.submitList(missionList.filter { it.contractor?.id == myId && it.status == "complete" })
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