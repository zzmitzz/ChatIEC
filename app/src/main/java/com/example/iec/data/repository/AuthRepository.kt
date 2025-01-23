package com.example.iec.data.repository

import android.util.Log
import com.example.iec.data.APIResult
import com.example.iec.data.remote.AuthRemote
import com.example.iec.data.remote.LoginRequest
import com.example.iec.data.remote.LoginResponse
import com.google.android.gms.vision.clearcut.LogUtils
import javax.inject.Inject


interface AuthRepository {
    suspend fun doLogin(
        username: String,
        password: String
    ): APIResult<LoginResponse>
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
}