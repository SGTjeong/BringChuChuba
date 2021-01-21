package com.bring.chuchuba.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bring.chuchuba.MemberService
import com.bring.chuchuba.model.ChangeDeviceTokenRequestBody
import com.bring.chuchuba.model.ChangeNicknameRequestBody
import com.bring.chuchuba.model.Member
import com.bring.chuchuba.model.family.CreateFamilyRequestBody
import com.bring.chuchuba.model.family.JoinFamilyRequestBody
import com.bring.chuchuba.model.mission.Mission
import com.bring.chuchuba.model.mission.MissionCreator
import com.bring.chuchuba.model.mission.MissionsItem
import com.bring.chuchuba.viewmodel.home.buildlogic.BaseViewModel
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeEvent
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.dynamiclinks.ktx.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    // 가족
    private val _inviteLink : MutableLiveData<String> = MutableLiveData()
    val inviteLink : LiveData<String> get() = _inviteLink

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnLogin -> onLogin()
            is HomeEvent.OnCreateFamily -> onCreateFamily(event.familyName)
            is HomeEvent.OnJoinFamily -> onJoinFamily(event.familyId)
            is HomeEvent.OnCreateFamilyLink -> onCreateFamilyLink()
            is HomeEvent.OnLoadMission -> onLoadMission()
            is HomeEvent.OnCreateMission -> onCreateMission(event.title, event.description,
                event.reward, event.expireAt)
            is HomeEvent.OnChangeNickname -> onChangeNickname(event.nick)
            is HomeEvent.OnCompleteMission -> onCompleteMission(event.mission)
            is HomeEvent.OnDeleteMission -> onDeleteMission(event.mission_uid)
        }
    }

    private fun onDeleteMission(mission_uid : String) = launch {
        memberService.deleteMission(mission_uid)
    }

    private fun onCreateFamilyLink() = launch {
        val shortLinkTask = Firebase.dynamicLinks.shortLinkAsync {
            myInfo.value?.familyId?:return@shortLinkAsync
            link = Uri.parse("https://bring.chuchuba.com/family?fKey=${myInfo.value?.familyId}")
            domainUriPrefix = "https://bringchuchuba.page.link"
            // Set parameters
            // ...
        }.addOnSuccessListener { (shortLink, flowchartLink) ->
            // Short link created, processShortLink(shortLink, flowchartLink)
            Log.d(TAG, "HomeViewModel ~ onCreateFamilyLink() called $shortLink")
            _inviteLink.postValue(shortLink.toString())
        }.addOnFailureListener {
            Log.e(TAG, "onCreateFamilyLink: $it")
        }.addOnCompleteListener {

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
        Log.d(TAG, "HomeViewModel ~ onLogin() called")
        try {
            applyMyInfo(
                memberService.getMyInfo().also { _myInfo.postValue(it) }
            )
            /**
             * 1. 토큰 처리 : onNewToken에서 처리를 하고싶은데 Header값을 받아오기 전에 실행 되서 request오류
             * onNewToken에서 핸들러로 Header값을 계속 읽어서 null이 아니면 보내는 방식으로 해도 될지?
             * 더 좋은방법이 있다면..
             * 2. changeDeviceToken의 response의 body가 null이라 Gson으로 변환이 안되는거 같아
             * Call<JsonObject>로 받았습니다.
             */
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new FCM registration token
                val token = task.result ?: return@OnCompleteListener
                memberService.changeDeviceToken(ChangeDeviceTokenRequestBody(token))
                    .enqueue(object : Callback<JsonObject>{
                        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                            Log.d(TAG, "HomeViewModel ~ onResponse() called $response")
                        }

                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                            Log.e(TAG, "onFailure: ", t)
                        }
                    })
            })
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