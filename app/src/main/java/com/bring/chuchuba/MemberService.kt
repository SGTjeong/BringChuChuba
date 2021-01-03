package com.bring.chuchuba

import com.bring.chuchuba.model.Member
import retrofit2.Call
import retrofit2.http.GET

interface MemberService {
    @GET("/member")
    suspend fun getMyInfo() : Member.MemberGetResult
}