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
            is HomeEvent.OnLoad -> onLoad()
            is HomeEvent.OnFamilyRequest -> onCreateFamily(event.familyName)
            is HomeEvent.OnMissionCreateRequest -> onCreateMission(event.title, event.description, event.reward)
        }
    }


    // myinfo프래그먼트 -> 가족만들기
    private fun onCreateFamily(familyName : String) = launch {
        Log.d(TAG, "HomeViewModel ~ createFamily() called")
        try{
            val createFamily = memberService.createFamily(CreateFamilyRequestBody(familyName))
            Log.d(TAG, "HomeViewModel ~ checkCreatedFaimly() called ${createFamily.name}" +
                    "${createFamily.members.size}")
        } catch (e : Exception){
            Log.e(TAG, "createFamily: $e")
        }
    }

    // 홈프래그먼트 -> 미션만들기
    private fun onCreateMission(title : String, description : String, reward : String) = launch {
        Log.d(TAG, "HomeViewModel ~ createMission() called")
        try {
            memberService.createMission(
                MissionCreator(
                    description,
                    myInfo.value!!.familyId.toInt(),
                    reward,
                    title)
            )
            // 미션 생성후, 미션 다시 불러오기.
            // 등록된 미션이 응답으로 오기 때문에, 간단한 확인 메세지를 띄워도 좋을듯
            onLoad()
        } catch (e: Exception) {
            Log.e(TAG, "createMission: $e")
        }
    }

    private fun onLoad() = launch {
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
            onLoad()
        }
    }
}