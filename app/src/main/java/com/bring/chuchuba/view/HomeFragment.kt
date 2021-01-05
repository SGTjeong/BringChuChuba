package com.bring.chuchuba.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bring.chuchuba.R
import com.bring.chuchuba.adapter.RecyclerViewAdapter
import com.bring.chuchuba.adapter.base.BaseViewHolder
import com.bring.chuchuba.adapter.base.MyItem
import com.bring.chuchuba.adapter.base.MyList
import com.bring.chuchuba.databinding.FragmentHomeBinding
import com.bring.chuchuba.model.mission.Mission
import com.bring.chuchuba.model.mission.MissionsItem
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeEvent
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeInjector
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val TAG: String = "로그 ${this.javaClass.simpleName}"

    private lateinit var binding : FragmentHomeBinding
    private lateinit var homeViewModel : HomeViewModel
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var completedAdapter : RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        /**
         *  binding 객체에 vm과 lifecycleowner를 지정해줘야만 livedata가 변화할 때 그것이 ui에 반영됩니다.
         * */
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run{
            homeViewModel = ViewModelProvider(
                this,
                HomeInjector().provideViewModelFactory()
            ).get(
                HomeViewModel::class.java
            )
//            homeViewModel.handleEvent(HomeEvent.OnLoad)
        }
    }

    private fun observeViewModels(){
        homeViewModel.myInfo.observe(
            viewLifecycleOwner,
            Observer { member ->
                member?:return@Observer
            }
        )

        homeViewModel.missionData.observe(
            viewLifecycleOwner,
            Observer { missionList ->
                missionList?:return@Observer
                /**
                 * 완료된 미션과 그렇지 않은 미션으로 구분
                 */
                adapter.setList(missionList.filter { it.status=="todo" } as ArrayList<MyItem>)
                adapter.notifyDataSetChanged()

                completedAdapter.setList(missionList.filter { it.status=="todo" } as ArrayList<MyItem>)
                completedAdapter.notifyDataSetChanged()
            }
        )
    }

    fun createMission(){
        (activity as MainActivity).createMission()
    }

}