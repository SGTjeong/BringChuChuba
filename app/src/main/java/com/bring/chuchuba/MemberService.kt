package com.bring.chuchuba

import com.bring.chuchuba.model.Member
import com.bring.chuchuba.model.family.CreateFamily
import com.bring.chuchuba.model.family.CreateFamilyRequestBody
import com.bring.chuchuba.model.family.JoinFamilyRequestBody
import com.bring.chuchuba.model.family.JoinFamilyResponse
import com.bring.chuchuba.model.mission.Mission
import com.bring.chuchuba.model.mission.MissionCreator
import com.bring.chuchuba.model.mission.MissionsItem
import retrofit2.http.*

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
    ) : MissionsItem

    @GET("/family")
    suspend fun getFamily(

    )

    @POST("/family")
    suspend fun createFamily(
        @Body name : CreateFamilyRequestBody
    ) : CreateFamily

    @PUT("/family")
    suspend fun joinFamily(
        @Body familyId : JoinFamilyRequestBody
    ) : JoinFamilyResponse
}