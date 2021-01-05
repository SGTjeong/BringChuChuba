package com.bring.chuchuba.viewmodel.home.buildlogic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bring.chuchuba.MemberService
import com.bring.chuchuba.model.Member
import com.bring.chuchuba.model.family.CreateFamily
import com.bring.chuchuba.model.family.CreateFamilyRequestBody
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

    fun createFamily(familyName : String) = launch {
        Log.d(TAG, "HomeViewModel ~ createFamily() called")
        try{
            checkCreatedFaimly(memberService.createFamily(CreateFamilyRequestBody(familyName)))
        } catch (e : Exception){
            Log.e(TAG, "createFamily: $e")
        }
    }

    private fun checkCreatedFaimly(createFamily: CreateFamily) {
        Log.d(TAG, "HomeViewModel ~ checkCreatedFaimly() called ${createFamily.name}" +
                "${createFamily.members.size}")
    }

    fun createMission(missionContent: String) = launch {
        Log.d(TAG, "HomeViewModel ~ createMission() called")
        try {
            val mlist = missionContent.split(",")
            memberService.createMission(
                MissionCreator(
                    mlist[1],
                    myInfo.value!!.familyId.toInt(),
                    mlist[2],
                    mlist[0])
            )
            /**
             * 1.미션을 생성하고, 2.미션리스트를 갱신하고 싶은데, OnLoad를 부르면 순서대로 실행이 될까요??
             * */
            OnLoad()

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

    // When viewModel is notified a HomeEvent.OnStart, this function will be called.
    private fun onStart() = launch {
        Log.d(TAG, "HomeViewModel ~ onStart() called")

        applyMyInfo(
            memberService.getMyInfo().also { _myInfo.postValue(it) }
        )
    }

    private fun applyMyInfo(myInfo : Member.MemberGetResult){
        Log.d(TAG, "applyMyInfo : ${myInfo.id}")
        if (myInfo.familyId==-1L){
            title.postValue(
                "${myInfo.id}님 환영합니다. 방을 만드세요!"
            )
        } else {
            title.postValue(
                "${myInfo.familyId}의 ${myInfo.id}님 환영합니다"
            )
            OnLoad()
        }
    }
}