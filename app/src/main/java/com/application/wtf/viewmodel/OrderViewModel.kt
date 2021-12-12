package com.application.wtf.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.wtf.network.Resource
import com.application.wtf.payload.OrderHistoryResponse
import com.application.wtf.payload.OrderRequest
import com.application.wtf.payload.OrderResponse
import com.application.wtf.repository.MainCategoryRepository
import com.application.wtf.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class OrderViewModel(
    private val categoryRepository: MainCategoryRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {

    private var _orderResponse: MutableLiveData<Resource<OrderResponse>>? = null
    val orderResponse: LiveData<Resource<OrderResponse>>
        get() {
            if (_orderResponse == null) {
                _orderResponse = MutableLiveData()
            }
            return _orderResponse!!
        }

    private var _orderHistory: MutableLiveData<Resource<List<OrderHistoryResponse>>>? = null
    val orderHistoryResponse: LiveData<Resource<List<OrderHistoryResponse>>>
        get() {
            if (_orderHistory == null) {
                _orderHistory = MutableLiveData()
                requestOrderHistory()
            }
            return _orderHistory!!
        }

    fun requestOrder(categoryID: List<String>) {
        val item = OrderRequest(categoryID)
        disposable.add(
            categoryRepository.order(userRepository.userLoginToken, item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _orderResponse?.postValue(Resource.loading())
                }
                .subscribe({ result ->
                    _orderResponse?.postValue(Resource.success(result))
                }, {
                    _orderResponse?.postValue(Resource.error(null))
                })
        )
    }

    private fun requestOrderHistory() {
        disposable.add(
            categoryRepository.orderHistory(userRepository.userLoginToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _orderHistory?.postValue(Resource.loading())
                }
                .subscribe({ result ->
                    _orderHistory?.postValue(Resource.success(result))
                }, {
                    _orderHistory?.postValue(Resource.error(null))
                })
        )
    }
}