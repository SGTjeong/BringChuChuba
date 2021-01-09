package com.bring.chuchuba.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bring.chuchuba.MemberService
import com.bring.chuchuba.model.Member
import com.bring.chuchuba.model.family.CreateFamilyRequestBody
import com.bring.chuchuba.model.family.JoinFamilyRequestBody
import com.bring.chuchuba.model.mission.Mission
import com.bring.chuchuba.model.mission.MissionCreator
import com.bring.chuchuba.viewmodel.home.buildlogic.BaseViewModel
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeEvent
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
            is HomeEvent.OnLogin -> onLogin()
            is HomeEvent.OnCreateFamily -> onCreateFamily(event.familyName)
            is HomeEvent.OnJoinFamily -> onJoinFamily(event.familyId)
            is HomeEvent.OnLoadMission -> onLoadMission()
            is HomeEvent.OnCreateMission -> onCreateMission(event.title, event.description,
                event.reward, event.expireAt)
        }
    }

    /**
     *  myinfo프래그먼트 -> 가족 참여
     *  가족만들기, 참여는 콜백으로 성공여부를 알려주고 싶은데, 그러면 뷰모델에서 뷰에게 의존성이 생길거같아서,
     *  에러도 발생
     *  라이브데이터를 활용해아할까?
     */
    private fun onJoinFamily(familyId: String) = launch{
        Log.d(TAG, "HomeViewModel ~ onJoinFamily() called")
        try{
            val joinFamily = memberService.joinFamily(JoinFamilyRequestBody(familyId.toInt()))
            Log.d(TAG, "HomeViewModel ~ onJoinFamily() success to ${joinFamily.name}")
            onLogin()
        }catch (e : Exception){
            Log.e(TAG, "onJoinFamily: $e", )
        }
    }


    // myinfo프래그먼트 -> 가족만들기
    private fun onCreateFamily(familyName : String) = launch {
        Log.d(TAG, "HomeViewModel ~ createFamily() called")
        try{
            val createFamily = memberService.createFamily(CreateFamilyRequestBody(familyName))
            Log.d(TAG, "HomeViewModel ~ checkCreatedFaimly() called ${createFamily.name}" +
                    "${createFamily.members.size}")
            onLogin()
            
//            callback(createFamily.name)
        } catch (e : Exception){
            Log.e(TAG, "createFamily: $e")
//            callback("")
        }
    }

    // 홈프래그먼트 -> 미션만들기
    private fun onCreateMission(title : String, description : String,
                                reward : String, expireAt : String) = launch {
        Log.d(TAG, "HomeViewModel ~ createMission() called")
        try {
            memberService.createMission(
                MissionCreator(
                    description,
                    myInfo.value!!.familyId,
                    reward,
                    title,
                    expireAt)
            )
            onLoadMission()
        } catch (e: Exception) {
            Log.e(TAG, "createMission: $e")
        }
    }

    private fun onLoadMission() = launch {
        Log.d(TAG, "HomeViewModel ~ OnLoad() called")
        try {
            _missionData.postValue(
                myInfo.value?.familyId?.let { memberService.getMissions(it.toInt()) }
            )
        } catch (e: Exception) {
            Log.e(TAG, "OnLoad: $e")
        }
    }

    // When viewModel is notified a HomeEvent.OnStart, this function will be called.
    private fun onLogin() = launch {
        Log.d(TAG, "HomeViewModel ~ onStart() called")
        applyMyInfo(
            memberService.getMyInfo().also { _myInfo.postValue(it) }
        )
    }

    private fun applyMyInfo(myInfo : Member.MemberGetResult){
        Log.d(TAG, "applyMyInfo : ${myInfo.id}")
        if (myInfo.familyId.isNullOrBlank()){
            title.postValue(
                "${myInfo.id}님 환영합니다. 방을 만드세요!"
            )
        } else {
            title.postValue(
                "${myInfo.familyId}의 ${myInfo.id}님 환영합니다"
            )
            onLoadMission()
        }
    }
}