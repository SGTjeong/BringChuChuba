package com.bring.chuchuba.model.mission.response

data class MissionResponse(
    val client: Client,
    val contractor: Contractor,
    val description: String,
    val familyId: Int,
    val id: Int,
    val reward: String,
    val status: String,
    val title: String
)