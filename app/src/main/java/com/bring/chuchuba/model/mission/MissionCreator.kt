package com.bring.chuchuba.model.mission

data class MissionCreator(
    val description: String,
    val familyId: String?,
    val reward: String,
    val title: String,
    val expireAt: String
)