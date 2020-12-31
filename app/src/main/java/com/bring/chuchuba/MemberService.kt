package com.bring.chuchuba

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MemberService {
    @GET("/member")
    fun getMyInfo() : Call<Member.MemberGetResult>

}