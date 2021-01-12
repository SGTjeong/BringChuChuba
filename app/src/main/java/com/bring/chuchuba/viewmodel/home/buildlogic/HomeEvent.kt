package com.bring.chuchuba.viewmodel.home.buildlogic

sealed class HomeEvent {
    object OnLogin : HomeEvent()

    // familly
    data class OnCreateFamily(val familyName : String) : HomeEvent()
    data class OnJoinFamily(val familyId : String) : HomeEvent()

    // mission
    object OnLoadMission : HomeEvent()
    data class OnCreateMission(val title : String, val description : String,
                               val reward : String, val expireAt : String) : HomeEvent()
}