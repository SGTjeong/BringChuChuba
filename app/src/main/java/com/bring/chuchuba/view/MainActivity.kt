package com.bring.chuchuba.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bring.chuchuba.*
import com.bring.chuchuba.adapter.CustomFragmentAdapter
import com.bring.chuchuba.viewmodel.MainViewModel
import com.bring.chuchuba.databinding.ActivityMainBinding
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeEvent
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeInjector
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeViewModel
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val TAG = "로그 ${this.javaClass.simpleName}"

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var homeViewModel : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        homeViewModel = ViewModelProvider(
            this,
            HomeInjector().provideViewModelFactory()
        ).get(
            HomeViewModel::class.java
        )

        connectAdapter()
        getMyInfo()
        observeViewModels()
    }

    private fun observeViewModels(){
        homeViewModel.myInfo.observe(
            this,
            Observer { member ->
                member?:return@Observer
                showToast("member id : ${member.id}, family id : ${member.familyId}")
            }
        )
    }

    private fun getMyInfo() {
        Log.d(TAG, "getMemberId")
        homeViewModel.handleEvent(HomeEvent.OnStart)
    }


    // 뷰 페이저와 프레그먼트,탭레이아웃 연결
    private fun connectAdapter() {
        val tabTextList : List<String> = listOf("홈", "달력", "나의 상태")
        val tabIconList : List<Drawable> = listOf()
        binding.viewPager2.adapter = CustomFragmentAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) {
                tab, position ->
            // tab.setIcon(tabIconList[position])
            tab.text = tabTextList[position]
        }.attach()
    }

}