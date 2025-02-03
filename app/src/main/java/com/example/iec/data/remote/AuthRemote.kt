package com.example.iec.data.remote

import com.example.iec.ui.model.UserInfo
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

@Serializable
data class LoginRequest(
    @SerializedName("user") val username: String,
    val password: String
)

@Serializable
data class LoginResponse(
    @SerializedName("_id") val id: String,
    val name: String,
    val username: String,
    val password: String
)

@Serializable
data class LoginRequest(
    @SerializedName("user") val username: String,
    val password: String
)


@Serializable
data class RegisterResponse(

)


@Serializable
data class  IECResponse<T>(
    val isSuccess: Boolean,
    val result: T
)

interface AuthRemote {
    @POST("validateauth")
    suspend fun validateAuth(
        @Body requestBody: LoginRequest
    ): IECResponse<LoginResponse>

    @POST("userdetail")
    suspend fun getDetailUserInfo(@Body user: com.google.gson.JsonObject): IECResponse<UserInfo>
}