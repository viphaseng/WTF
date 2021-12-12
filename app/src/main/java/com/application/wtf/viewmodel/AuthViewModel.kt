package com.application.wtf.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.wtf.network.Resource
import com.application.wtf.payload.LoginRequest
import com.application.wtf.payload.RegisterRequest
import com.application.wtf.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class AuthViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    private var _username: MutableLiveData<Resource<String>>? = null
    val username: LiveData<Resource<String>>
        get() {
            if (_username == null) {
                _username = MutableLiveData()
            }
            return _username!!
        }

    fun register(username: String, password: String, fullName: String, dob: String) {
        val request = RegisterRequest(username, password, fullName, dob)
        disposable.add(
            userRepository.register(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _username?.postValue(Resource.loading()) }
                .subscribe({ result ->
                    _username?.postValue(Resource.success(result.fullName))
                    userRepository.userLoginToken = result.token
                }, { error ->
                    if (error is IOException) {
                        _username?.postValue(Resource.error(error.message))
                    }
                })
        )
    }

    fun login(username: String, password: String) {
        val requestLogin = LoginRequest(username, password)
        disposable.add(
            userRepository.login(requestLogin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _username?.postValue(Resource.loading()) }
                .subscribe({ result ->
                    _username?.postValue(Resource.success(result.fullName))
                    userRepository.userLoginToken = result.token

                }, { error ->
                    if (error is IOException) {
                        _username?.postValue(Resource.error(error.message))
                    }
                })
        )
    }
}