package com.bring.chuchuba.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bring.chuchuba.MemberService
import com.bring.chuchuba.model.ChangeNicknameRequestBody
import com.bring.chuchuba.model.Member
import com.bring.chuchuba.model.family.CreateFamilyRequestBody
import com.bring.chuchuba.model.family.JoinFamilyRequestBody
import com.bring.chuchuba.model.mission.Mission
import com.bring.chuchuba.model.mission.MissionCreator
import com.bring.chuchuba.model.mission.MissionsItem
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

    private val _title : MutableLiveData<String> = MutableLiveData("Welcome")
    val title : LiveData<String> get() = _title

    // 미션
    private val _missionData = MutableLiveData<Mission>()
    val missionData: LiveData<Mission> get() = _missionData

    // 성공여부
    private val _jobSucceedOrFail : MutableLiveData<String> = MutableLiveData()
    val jobSucceedOrFail : LiveData<String> get() = _jobSucceedOrFail


    override fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnLogin -> onLogin()
            is HomeEvent.OnCreateFamily -> onCreateFamily(event.familyName)
            is HomeEvent.OnJoinFamily -> onJoinFamily(event.familyId)
            is HomeEvent.OnLoadMission -> onLoadMission()
            is HomeEvent.OnCreateMission -> onCreateMission(event.title, event.description,
                event.reward, event.expireAt)
            is HomeEvent.OnChangeNickname -> onChangeNickname(event.nick)
            is HomeEvent.OnCompleteMission -> onCompleteMission(event.mission)
        }
    }

    private fun onCompleteMission(mission: MissionsItem) = launch{
        try{
            memberService.completeMission(mission.id)
            onLoadMission()
        }catch (e : Exception){
            Log.e(TAG, "onCompleteMission: $e")
        }
    }

    private fun onChangeNickname(nick: String) = launch{
        Log.d(TAG, "HomeViewModel ~ onChangeNickname() called")
        try{
            memberService.changeNickName(ChangeNicknameRequestBody(nick))
            onLogin()
            _jobSucceedOrFail.postValue("닉네임이 \"$nick\"로 변경되었습니다")
        }catch (e : Exception){
            Log.e(TAG, "onChangeNickname: $e")
            _jobSucceedOrFail.postValue("닉네임 변경 실패")
        }
    }

    private fun onJoinFamily(familyId: String) = launch{
        Log.d(TAG, "HomeViewModel ~ onJoinFamily() called")
        try{
            val joinFamily = memberService.joinFamily(JoinFamilyRequestBody(familyId))
            Log.d(TAG, "HomeViewModel ~ onJoinFamily() success to ${joinFamily.name}")
            onLogin()
            _jobSucceedOrFail.postValue("\"${joinFamily.name}\"에 참여하였습니다!")
        }catch (e : Exception){
            Log.e(TAG, "onJoinFamily: $e", )
            _jobSucceedOrFail.postValue("가족 참여 실패!")
        }
    }


    // myinfo프래그먼트 -> 가족만들기
    private fun onCreateFamily(familyName : String) = launch {
        Log.d(TAG, "HomeViewModel ~ createFamily() called")
        try{
            val createFamily = memberService.createFamily(CreateFamilyRequestBody(familyName))
            Log.d(TAG, "HomeViewModel ~ checkCreatedFaimly() called ${createFamily.name}" +
                    "${createFamily.members.size}")
            _jobSucceedOrFail.postValue("Welcome to \"${createFamily.name}\"")
            onLogin()
        } catch (e : Exception){
            Log.e(TAG, "createFamily: $e")
            _jobSucceedOrFail.postValue("가족 만들기 실패!")
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
            _jobSucceedOrFail.postValue("미션 \"$title\" 생성 완료!")
            onLoadMission()
        } catch (e: Exception) {
            Log.e(TAG, "createMission: $e")
            _jobSucceedOrFail.postValue("미션 만들기 실패!")
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
        try {
            applyMyInfo(
                memberService.getMyInfo().also { _myInfo.postValue(it) }
            )
        }catch (e : Exception){
            Log.e(TAG, "onLogin: $e")
        }
    }

    private fun applyMyInfo(myInfo : Member.MemberGetResult){
        Log.d(TAG, "applyMyInfo : ${myInfo.id}")
        if (myInfo.familyId.isNullOrBlank()){
            _title.postValue(
                "${myInfo.nickname ?: "새로운 참가자"}님 환영합니다. 방을 만드세요!"
            )
        } else {
            _title.postValue(
                "${myInfo.familyName}의 ${myInfo.nickname ?: "새로운 참가자"}님 환영합니다"
            )
            onLoadMission()
        }
    }
}