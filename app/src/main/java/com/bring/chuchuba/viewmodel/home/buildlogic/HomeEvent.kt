package com.bring.chuchuba.viewmodel.home.buildlogic

sealed class HomeEvent {
    object OnStart : HomeEvent()
    object OnLoad : HomeEvent()
}