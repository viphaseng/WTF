package com.application.wtf.network

data class Resource<out T>(
    val status: Status,
    val data: T?
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        EMPTY
    }

    companion object {

        fun <T> success(data: T): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data
            )
        }

        fun <T> error(data: T? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                data
            )
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                Status.LOADING,
                data
            )
        }

    }
}