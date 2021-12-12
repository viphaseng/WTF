package com.application.wtf.network

import com.application.wtf.payload.CategoryDetailResponse
import com.application.wtf.payload.CategoryItemList
import com.application.wtf.payload.LoginRequest
import com.application.wtf.payload.LoginResponse
import com.application.wtf.payload.OrderHistoryResponse
import com.application.wtf.payload.OrderRequest
import com.application.wtf.payload.OrderResponse
import com.application.wtf.payload.RegisterRequest
import com.application.wtf.payload.RegisterResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface RetroService {

    @POST("register")
    fun register(@Body request: RegisterRequest): Single<RegisterResponse>

    @POST("login")
    fun login(@Body request: LoginRequest): Single<LoginResponse>

    @GET("categories")
    fun getListCategory(): Single<List<CategoryItemList>>

    @GET("category/{categoryID}")
    fun getCategoryDetail(@Path(value = "categoryID") categoryID: String): Single<CategoryDetailResponse>

    @POST("orders")
    fun order(@Header("Authorization") authToken: String, @Body request: OrderRequest): Single<OrderResponse>

    @GET("orders")
    fun orderHistory(@Header("Authorization") authToken: String): Single<List<OrderHistoryResponse>>
}

