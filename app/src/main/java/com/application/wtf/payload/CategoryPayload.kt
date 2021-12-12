package com.application.wtf.payload

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class CategoryItemList(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
)

data class CategoryDetailResponse(
    @SerializedName("menu")
    val menu: List<ItemDetail>,
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String
)

@Parcelize
data class ItemDetail(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("category")
    val category: String,
) : Parcelable

data class OrderRequest(
    @SerializedName("items")
    val items: List<String>
)

data class OrderResponse(
    @SerializedName("menu")
    val items: List<ItemDetail>,
    @SerializedName("_id")
    val id: String,
    @SerializedName("user")
    val user: User,
    @SerializedName("date")
    val date: String

)

data class User(
    @SerializedName("_id")
    val id: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String,
)

data class OrderHistoryResponse(
    @SerializedName("menu")
    val items: List<ItemDetail>,
    @SerializedName("_id")
    val id: String,
    @SerializedName("user")
    val user: String,
    @SerializedName("date")
    val date: String

)


