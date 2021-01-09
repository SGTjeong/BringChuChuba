package com.bring.chuchuba.model.family

import com.bring.chuchuba.model.mission.Client
import com.bring.chuchuba.model.mission.MissionsItem

data class CreateFamily(
    val id: String?,
    val members: List<Client>,
    val missions: List<MissionsItem>,
    val name: String
)