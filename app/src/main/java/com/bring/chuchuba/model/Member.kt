package com.bring.chuchuba.model

data class Member(
    val uid : Long
){
    data class MemberGetResult(
        val familyId : String?,
        val id : String?,
        val nickname : String?,
        val point : String?
        )
}