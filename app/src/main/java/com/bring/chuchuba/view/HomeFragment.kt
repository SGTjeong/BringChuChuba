package com.bring.chuchuba.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bring.chuchuba.R
import com.bring.chuchuba.adapter.RecyclerViewAdapter
import com.bring.chuchuba.adapter.base.BaseViewHolder
import com.bring.chuchuba.adapter.base.MyItem
import com.bring.chuchuba.databinding.FragmentHomeBinding
import com.bring.chuchuba.databinding.MakeFamilyDialogBinding
import com.bring.chuchuba.extension.FragmentDialog
import com.bring.chuchuba.showToast
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeEvent
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeInjector
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeViewModel
import kotlin.concurrent.fixedRateTimer

class HomeFragment : Fragment() {

    private val TAG: String = "로그 ${this.javaClass.simpleName}"

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var completedAdapter: RecyclerViewAdapter

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
        adapter = RecyclerViewAdapter(BaseViewHolder.MISSION)
        binding.homeRecyclerView.adapter = adapter
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(context)

        completedAdapter = RecyclerViewAdapter(BaseViewHolder.MISSION)
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
                adapter.setList(missionList.filter { it.status == "todo" } as ArrayList<MyItem>)
                adapter.notifyDataSetChanged()

                completedAdapter.setList(missionList.filter { it.status == "todo" } as ArrayList<MyItem>)
                completedAdapter.notifyDataSetChanged()
            }
        )
    }

    /**
     * 임시로 가족이름란에 "title, description, reward" 형식으로 보내면 등록
     * 데이터바인딩을 하려는데 다이얼로그가 작게나오는 현상..
     */
    fun createMission() {
        val dlg = FragmentDialog()
        dlg.show(parentFragmentManager, "mission")
//        dlg.binding.sendSubmit.setOnClickListener {
//            val missionContent = dlg.binding.familyName.text.trim().toString()
//            if (missionContent != "") {
//                val mlist = missionContent.split(",")
//                homeViewModel.handleEvent(
//                    HomeEvent.OnMissionCreateRequest(
//                        title = mlist[0], description = mlist[1], reward = mlist[2]
//                    )
//                )
//                dlg.dismiss()
//            } else {
//                showToast("빈칸입니다")
//            }
//        }
//        dlg.binding.closeSubmit.setOnClickListener {
//            dlg.dismiss()
//        }
    }
}