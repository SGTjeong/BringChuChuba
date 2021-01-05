package com.bring.chuchuba.model.family

import com.bring.chuchuba.model.mission.MissionsItem

data class CreateFamily(
    val id: Int,
    val members: List<Member>,
    val missions: List<MissionsItem>,
    val name: String
)