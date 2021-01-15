package com.bring.chuchuba.model.mission

import com.bring.chuchuba.adapter.base.MyItem
import com.bring.chuchuba.model.Member

data class MissionsItem(
    val client: Member.MemberGetResult,
    val contractor: Member.MemberGetResult,
    val description: String,
    val createdAt: String,
    val expireAt: String,
    val modifiedAt: String,
    val familyId: String,
    val id: String,
    val reward: String,
    val status: String,
    val title: String
) : MyItem()