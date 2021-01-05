package com.bring.chuchuba.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bring.chuchuba.*
import com.bring.chuchuba.adapter.CustomFragmentStateAdapter
import com.bring.chuchuba.viewmodel.MainViewModel
import com.bring.chuchuba.databinding.ActivityMainBinding
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeEvent
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeInjector
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

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
                Log.d(TAG,"member id : ${member.id}, family id : ${member.familyId}")
            }
        )
    }

    private fun getMyInfo() {
        Log.d(TAG, "getMemberId")
        homeViewModel.handleEvent(HomeEvent.OnStart)
    }

    private fun connectAdapter() {
        val tabTextList : List<String> = listOf("홈", "나의 상태")
        val tabIconList : List<Drawable> = listOf()
        binding.viewPager2.adapter = CustomFragmentStateAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) {
                tab, position ->
            // tab.setIcon(tabIconList[position])
            tab.text = tabTextList[position]
        }.attach()
    }

}