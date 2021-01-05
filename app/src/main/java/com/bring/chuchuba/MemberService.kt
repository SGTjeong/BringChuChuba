package com.bring.chuchuba

import com.bring.chuchuba.model.Member
import com.bring.chuchuba.model.family.CreateFamily
import com.bring.chuchuba.model.family.CreateFamilyRequestBody
import com.bring.chuchuba.model.mission.Mission
import com.bring.chuchuba.model.mission.MissionCreator
import com.bring.chuchuba.model.mission.response.MissionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MemberService {
    @GET("/member")
    suspend fun getMyInfo() : Member.MemberGetResult

    @GET("/mission")
    suspend fun getMissions(
        @Query("familyId")
        familyId: Int
    ): Mission

    @POST("/mission")
    suspend fun createMission(
        @Body mission : MissionCreator
    ) : MissionResponse

    @POST("/family")
    suspend fun createFamily(
        @Body name : CreateFamilyRequestBody
    ) : CreateFamily
}