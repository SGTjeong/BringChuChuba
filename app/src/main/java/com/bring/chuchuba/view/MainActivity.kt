package com.bring.chuchuba.view

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bring.chuchuba.*
import com.bring.chuchuba.adapter.CustomFragmentAdapter
import com.bring.chuchuba.databinding.ActivityMainBinding
import com.bring.chuchuba.viewmodel.HomeViewModel
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeEvent
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeInjector
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val TAG = "로그 ${this.javaClass.simpleName}"

    private lateinit var binding: ActivityMainBinding
    private lateinit var homeViewModel: HomeViewModel

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

    private fun observeViewModels() {
        homeViewModel.myInfo.observe(
            this,
            Observer { member ->
                member ?: return@Observer
                showToast("${member.nickname}님 환영합니다!")
            }
        )
        homeViewModel.jobSucceedOrFail.observe(this) { msg ->
            this.showToast(msg)
        }
    }

    private fun getMyInfo() {
        Log.d(TAG, "getMemberId")
        homeViewModel.handleEvent(HomeEvent.OnLogin)
    }

    // 뷰 페이저와 프레그먼트,탭레이아웃 연결
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun connectAdapter() {
        val tabIconList: List<Drawable?> = listOf(
            resources.getDrawable(R.drawable.ic_outline_assignment_24, null),
            resources.getDrawable(R.drawable.ic_baseline_calendar_today_24, null),
            resources.getDrawable(R.drawable.ic_outline_settings_24, null),
            )
        binding.mainViewpager.adapter = CustomFragmentAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.mainViewpager) { tab, position ->
            tab.icon = tabIconList[position]
        }.attach()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0 &&
            moveTaskToBack(false)
        ) return
        super.onBackPressed()
    }
}