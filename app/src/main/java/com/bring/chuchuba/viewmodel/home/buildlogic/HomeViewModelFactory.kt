package com.bring.chuchuba.viewmodel.home.buildlogic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bring.chuchuba.MemberService
import com.bring.chuchuba.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers

class HomeViewModelFactory(
    private val memberService: MemberService
) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(
            memberService,
            Dispatchers.IO
        ) as T
    }

}