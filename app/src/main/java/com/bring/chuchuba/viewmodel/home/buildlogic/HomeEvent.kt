package com.bring.chuchuba.viewmodel.home.buildlogic

import com.bring.chuchuba.model.mission.MissionsItem

sealed class HomeEvent {
    object OnLogin : HomeEvent()
    data class OnChangeNickname(val nick: String) : HomeEvent()

    // familly
    data class OnCreateFamily(val familyName : String) : HomeEvent()
    data class OnJoinFamily(val familyId : String) : HomeEvent()
    object OnCreateFamilyLink : HomeEvent()

    // mission
    object OnLoadMission : HomeEvent()
    data class OnDeleteMission(val mission_uid : String) : HomeEvent()
    data class OnCreateMission(val title : String, val description : String,
                               val reward : String, val expireAt : String) : HomeEvent()

    data class OnCompleteMission(val mission: MissionsItem) : HomeEvent()
    data class OnContractMission(val mission: MissionsItem) : HomeEvent()
}