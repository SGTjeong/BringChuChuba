package com.bring.chuchuba.viewmodel.home.buildlogic

sealed class HomeEvent {
    object OnStart : HomeEvent()
    object OnLoad : HomeEvent()
    data class OnFamilyRequest(val familyName : String) : HomeEvent()
    data class OnMissionCreateRequest(val title : String,
                                      val description : String, val reward : String) : HomeEvent()
}