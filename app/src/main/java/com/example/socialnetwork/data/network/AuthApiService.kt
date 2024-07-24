package com.example.socialnetwork.data.network

import com.example.socialnetwork.data.model.ForgetPasswordRequest
import com.example.socialnetwork.data.model.ForgetPasswordResponse
import com.example.socialnetwork.data.model.LoginRequest
import com.example.socialnetwork.data.model.LoginResponse
import com.example.socialnetwork.data.model.RegisterRequest
import com.example.socialnetwork.data.model.RequestChangePassword
import com.example.socialnetwork.data.model.ResponseRegister
import com.example.socialnetwork.data.model.ResponseSuccesObject
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthApiService {

    @POST("/auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): ResponseRegister

    @PATCH("/auth/changepassword")
    suspend fun changePassword(@Body changePassword: RequestChangePassword): ResponseSuccesObject

    @POST("/auth/forgotpassword")
    suspend fun forgetPassword(@Body forgotPasswordRequest: ForgetPasswordRequest): ForgetPasswordResponse

    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

}
