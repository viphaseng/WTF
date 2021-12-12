package com.application.wtf.repository

import com.application.wtf.network.RetroService
import com.application.wtf.payload.CategoryDetailResponse
import com.application.wtf.payload.CategoryItemList
import com.application.wtf.payload.OrderHistoryResponse
import com.application.wtf.payload.OrderRequest
import com.application.wtf.payload.OrderResponse
import io.reactivex.Single

class MainCategoryRepository(private val service: RetroService) {

    fun getMainCategory(): Single<List<CategoryItemList>> = service.getListCategory()
    fun getCategoryDetail(categoryID: String): Single<CategoryDetailResponse> = service.getCategoryDetail(categoryID)

    fun order(token: String, orderListId: OrderRequest): Single<OrderResponse> =
        service.order(token, orderListId)

    fun orderHistory(token: String): Single<List<OrderHistoryResponse>> =
        service.orderHistory(token)
}