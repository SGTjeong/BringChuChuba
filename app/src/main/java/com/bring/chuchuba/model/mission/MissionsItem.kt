package com.bring.chuchuba.model.mission

import com.bring.chuchuba.adapter.base.MyItem

data class MissionsItem(
    val client: Client,
    val contractor: Contractor,
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