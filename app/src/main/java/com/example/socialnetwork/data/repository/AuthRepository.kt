package com.example.socialnetwork.data.repository

import com.example.socialnetwork.data.model.ForgetPasswordRequest
import com.example.socialnetwork.data.model.ForgetPasswordResponse
import com.example.socialnetwork.data.model.LoginRequest
import com.example.socialnetwork.data.model.LoginResponse
import com.example.socialnetwork.data.model.RegisterRequest
import com.example.socialnetwork.data.model.RequestChangePassword
import com.example.socialnetwork.data.model.ResponseRegister
import com.example.socialnetwork.data.model.ResponseSuccesObject
import com.example.socialnetwork.data.network.AuthApiService
import javax.inject.Inject

val testLoginResponse = "{\n" +
        "    \"user\": {\n" +
        "        \"id\": 3,\n" +
        "        \"name\": \"Omar\",\n" +
        "        \"email\": \"oelbaraka@myges.fr\",\n" +
        "        \"password\": \"\$2b\$10\$2sAlPz3s8ou9PQSjR/7v7eOsvK0Chsmo7lg3s9.DOqeCqMAiPMmVu\",\n" +
        "        \"role\": \"USER\",\n" +
        "        \"isActive\": true,\n" +
        "        \"createdAt\": \"2024-06-19T17:25:57.978Z\",\n" +
        "        \"updatedAt\": \"2024-06-19T17:28:19.183Z\",\n" +
        "        \"passwordResetToken\": null,\n" +
        "        \"passwordResetTokenVerfied\": null,\n" +
        "        \"passwordResetTokenExpire\": null,\n" +
        "        \"passwordChangedAt\": null,\n" +
        "        \"emailVerificationToken\": null,\n" +
        "        \"emailVerified\": true\n" +
        "    },\n" +
        "    \"tokn\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MywiaWF0IjoxNzE4ODE4MTExLCJleHAiOjE3MTg5MDQ1MTF9.kWbrHoY3kpBV31s8YWypKhVxvgBOunDCyN3Nbtg42yk\"\n" +
        "}"

class AuthRepository @Inject constructor(private val authApiService: AuthApiService) {

    suspend fun register(registerRequest: RegisterRequest): ResponseRegister {
        return authApiService.register(registerRequest)
    }

    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return authApiService.login(loginRequest)
        //return Gson().fromJson(testLoginResponse, LoginResponse::class.java)
    }

    suspend fun forgetPassword(forgottenPasswordRequest: ForgetPasswordRequest): ForgetPasswordResponse {
        return authApiService.forgetPassword(forgottenPasswordRequest)
    }

    suspend fun changePassword(requestChangePassword: RequestChangePassword): ResponseSuccesObject {
        return authApiService.changePassword(requestChangePassword)
    }
}
