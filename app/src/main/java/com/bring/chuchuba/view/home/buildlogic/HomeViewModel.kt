package com.bring.chuchuba.view.home.buildlogic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bring.chuchuba.MemberService
import com.bring.chuchuba.model.Member
import com.bring.chuchuba.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(
    private val memberService: MemberService,
    uiContext: CoroutineContext
) : BaseViewModel<HomeEvent>(uiContext){
    private val TAG = "로그 ${this.javaClass.simpleName}"

    private val _myInfo : MutableLiveData<Member.MemberGetResult> = MutableLiveData()
    val myInfo : LiveData<Member.MemberGetResult> get() = _myInfo

    override fun handleEvent(event: HomeEvent) {
        when(event){
            is HomeEvent.OnStart -> onStart()
        }
    }

    private fun onStart() = launch {
        _myInfo.postValue(
            memberService.getMyInfo()
        )
    }


}