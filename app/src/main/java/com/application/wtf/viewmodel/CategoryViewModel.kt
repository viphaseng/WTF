package com.application.wtf.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.wtf.network.Resource
import com.application.wtf.payload.CategoryDetailResponse
import com.application.wtf.payload.CategoryItemList
import com.application.wtf.repository.MainCategoryRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CategoryViewModel(private val categoryRepository: MainCategoryRepository) : BaseViewModel() {

    var chipID: Int = 0

    private var _mainCategory: MutableLiveData<Resource<List<CategoryItemList>>>? = null
    val mainCategory: LiveData<Resource<List<CategoryItemList>>>
        get() {
            if (_mainCategory == null) {
                _mainCategory = MutableLiveData()
                loadMainCategory()
            }
            return _mainCategory!!
        }
    private var _categoryDetailResponse: MutableLiveData<Resource<CategoryDetailResponse>>? = null
    val categoryDetailResponse: LiveData<Resource<CategoryDetailResponse>>
        get() {
            if (_categoryDetailResponse == null) {
                _categoryDetailResponse = MutableLiveData()
            }
            return _categoryDetailResponse!!
        }

    private fun loadMainCategory() {
        disposable.add(
            categoryRepository.getMainCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _mainCategory?.postValue(Resource.loading()) }
                .subscribe({ result ->
                    _mainCategory?.postValue(Resource.success(result))
                }, {
                    _mainCategory?.postValue(Resource.error(null))
                })
        )
    }

    fun loadCategoryDetail(categoryID: String) {
        disposable.add(
            categoryRepository.getCategoryDetail(categoryID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _categoryDetailResponse?.postValue(Resource.loading())
                }
                .subscribe({ result ->
                    _categoryDetailResponse?.postValue(Resource.success(result))
                }, {
                    _categoryDetailResponse?.postValue(Resource.error(null))
                })
        )
    }
}