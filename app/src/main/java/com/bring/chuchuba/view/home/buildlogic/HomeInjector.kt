package com.bring.chuchuba.view.home.buildlogic

import androidx.lifecycle.ViewModel
import com.bring.chuchuba.MemberService
import com.bring.chuchuba.NetworkManager.retrofit

class HomeInjector : ViewModel(){
    private fun getMemberService() : MemberService {
        return retrofit.create(MemberService::class.java)
    }

    fun provideViewModelFactory() : HomeViewModelFactory =
        HomeViewModelFactory(
            getMemberService()
        )
}