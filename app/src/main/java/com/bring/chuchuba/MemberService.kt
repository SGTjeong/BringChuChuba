package com.bring.chuchuba

import com.bring.chuchuba.model.ChangeDeviceTokenRequestBody
import com.bring.chuchuba.model.ChangeNicknameRequestBody
import com.bring.chuchuba.model.Member
import com.bring.chuchuba.model.family.CreateFamilyRequestBody
import com.bring.chuchuba.model.family.FamilyResponse
import com.bring.chuchuba.model.family.JoinFamilyRequestBody
import com.bring.chuchuba.model.mission.DeleteMissionResponse
import com.bring.chuchuba.model.mission.Mission
import com.bring.chuchuba.model.mission.MissionCreator
import com.bring.chuchuba.model.mission.MissionsItem
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface MemberService {
    @GET("/member")
    suspend fun getMyInfo(): Member.MemberGetResult

    @PATCH("/member/device")
    fun changeDeviceToken(@Body deviceToken: ChangeDeviceTokenRequestBody): Call<JsonObject>

    @POST("/member/nickname")
    suspend fun changeNickName(
        @Body nickname: ChangeNicknameRequestBody
    ): Member.MemberGetResult

    @GET("/mission")
    suspend fun getMissions(
        @Query("familyId")
        familyId: Int
    ): Mission

    @POST("/mission")
    suspend fun createMission(
        @Body mission: MissionCreator
    ): MissionsItem

    @GET("/family")
    suspend fun getFamily(

    )

    @POST("/family")
    suspend fun createFamily(
        @Body name: CreateFamilyRequestBody
    ): FamilyResponse

    @PUT("/family")
    suspend fun joinFamily(
        @Body familyId: JoinFamilyRequestBody
    ): FamilyResponse

    @DELETE("/mission/{mission_uid}")
    suspend fun deleteMission(
        @Path("mission_uid") mission_uid : String
    ) : DeleteMissionResponse

    @PATCH("/mission/client/{mission_uid}")
    suspend fun completeMission(
        @Path("mission_uid") mission_uid: String
    ): MissionsItem

    @PATCH("/mission/contractor/{mission_uid}")
    suspend fun contractMission(
        @Path("mission_uid") mission_uid: String
    ): MissionsItem
}