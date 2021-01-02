package com.bring.chuchuba.model

data class Member(
    val uid : Long
){
    data class MemberGetResult(
        val uid : Long
    )
}