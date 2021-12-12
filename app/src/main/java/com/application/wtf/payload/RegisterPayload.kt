package com.application.wtf.payload

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String
)

data class RegisterResponse(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String,
    @SerializedName("token")
    val token: String
)
