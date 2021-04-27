package com.asa.viewmodelcoroutine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asa.viewmodelcoroutine.bean.Banner
import com.asa.viewmodelcoroutine.net.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel :ViewModel(){

    val bannerObservable  = MutableLiveData<List<Banner.DataBean>>()



    fun getBanner(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val result = RetrofitManager.serverManager.getBanner()
                bannerObservable.postValue(result.data)
            }
        }
    }



}