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

    private fun onStart() = launch {
        Log.d(TAG, "HomeViewModel ~ onStart() called")

        /**
         *  _myInfo.postValue(~)를 하고 바로 _myInfo.value를 참조하면 반영이 안되있을 수 있습니다. (title.postValue(myInfo!!.value)..처럼)
         *  _myInfo 대신 memberservice.getMyInfo()의 결과값을 title에 반영해야 npe가 뜨지 않습니다.
         * */
        applyMyInfo(
            memberService.getMyInfo().also { _myInfo.postValue(it) }
        )
    }

    private fun applyMyInfo(myInfo : Member.MemberGetResult){
        Log.d(TAG, "applyMyInfo : ${myInfo.id}")
        title.postValue(
            "${myInfo.familyId}의 ${myInfo.id}님 환영합니다"
        )
    }

}