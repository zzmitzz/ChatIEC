package com.example.iec.data.repository

import android.util.Log
import com.example.iec.data.APIResult
import com.example.iec.data.remote.AuthRemote
import com.example.iec.data.remote.LoginRequest
import com.example.iec.data.remote.LoginResponse
import com.example.iec.ui.model.UserInfo
import com.google.gson.JsonObject
import javax.inject.Inject


interface AuthRepository {
    suspend fun doLogin(
        username: String,
        password: String
    ): APIResult<LoginResponse>

    suspend fun getInfoUser(userID: String): APIResult<UserInfo>
}

class AuthRepositoryImpl @Inject constructor(
    val authRemote: AuthRemote
) : AuthRepository {
    override suspend fun doLogin(username: String, password: String) : APIResult<LoginResponse> {
        val response  = authRemote.validateAuth(LoginRequest(username,password))
        Log.d("AuthRepository",("${response}"))
        if(response.isSuccess){
            return APIResult.Success(response.result)
        }else{
            return APIResult.Failure("Error")
        }
    }

    override suspend fun getInfoUser(userID: String): APIResult<UserInfo> {
        val jsonBody = JsonObject()
        jsonBody.addProperty("user", userID)
        val response  =  authRemote.getDetailUserInfo(jsonBody)
        if(response.isSuccess){
            return APIResult.Success(response.result)
        }else{
            return APIResult.Failure("Error")
        }
    }
}