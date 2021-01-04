package com.bring.chuchuba.viewmodel.home.buildlogic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bring.chuchuba.MemberService
import com.bring.chuchuba.model.Member
import com.bring.chuchuba.model.mission.Mission
import com.bring.chuchuba.model.mission.MissionCreator
import com.bring.chuchuba.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(
    private val memberService: MemberService,
    uiContext: CoroutineContext
) : BaseViewModel<HomeEvent>(uiContext) {
    private val TAG = "로그 ${this.javaClass.simpleName}"

    // 개인정보
    private val _myInfo: MutableLiveData<Member.MemberGetResult> = MutableLiveData()
    val myInfo: LiveData<Member.MemberGetResult> get() = _myInfo

    val title : MutableLiveData<String> = MutableLiveData("Welcome")

    // 미션
    private val _missionData = MutableLiveData<Mission>()
    val missionData: LiveData<Mission> get() = _missionData

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnStart -> onStart()
            is HomeEvent.OnLoad -> OnLoad()
        }
    }

    fun createMission() = launch {
        Log.d(TAG, "HomeViewModel ~ createMission() called")
        try {
            memberService.createMission(
                MissionCreator(
                    "미션테스트",
                    myInfo.value!!.familyId.toInt(),
                    "100",
                    "설거지")
            )
        } catch (e: Exception) {
            Log.e(TAG, "createMission: $e")
        }

    }

    private fun OnLoad() = launch {
        Log.d(TAG, "HomeViewModel ~ OnLoad() called")
        try {
            _missionData.postValue(
                myInfo.value?.let { memberService.getMissions(it.familyId.toInt()) }
            )
        } catch (e: Exception) {
            Log.e(TAG, "OnLoad: $e")
        }
    }

    /**
     *  When viewModel is notified a HomeEvent.OnStart, this function will be called.
     * */
    private fun onStart() = launch {
        /**
         *  Data change in _myInfo will be observed in MainActivity
         * */
        Log.d(TAG, "HomeViewModel ~ onStart() called")
        val info = memberService.getMyInfo()
        _myInfo.postValue(info)
        Log.d(TAG, "HomeViewModel ~ onStart() called ${_myInfo.value}")
        _myInfo.value?.let {
            title.postValue(
                "${_myInfo.value!!.familyId}의 ${_myInfo.value!!.id}님 환영합니다"
            )
        }
    }
}