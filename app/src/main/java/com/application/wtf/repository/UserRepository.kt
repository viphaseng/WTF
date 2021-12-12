package com.application.wtf.repository

import com.application.wtf.network.RetroService
import com.application.wtf.payload.LoginRequest
import com.application.wtf.payload.LoginResponse
import com.application.wtf.payload.RegisterRequest
import com.application.wtf.payload.RegisterResponse
import com.application.wtf.preference.SharePreference
import io.reactivex.Single

class UserRepository(private val service: RetroService, private val sharePreference: SharePreference) {

    var userLoginToken: String
        get() = sharePreference.getString("login_token", "")
        set(value) = sharePreference.putString("login_token", "Bearer $value")

    fun register(request: RegisterRequest): Single<RegisterResponse> {
        return service.register(request)
    }

    fun login(request: LoginRequest): Single<LoginResponse> {
        return service.login(request)
    }
}