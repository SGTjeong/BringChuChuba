package com.bring.chuchuba.model.family

import com.bring.chuchuba.model.Member
import com.bring.chuchuba.model.mission.MissionsItem

data class FamilyResponse(
    val id: String?,
    val members: List<Member.MemberGetResult>,
    val missions: List<MissionsItem>,
    val name: String
)