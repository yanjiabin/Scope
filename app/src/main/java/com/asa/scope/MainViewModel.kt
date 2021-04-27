package com.asa.scope

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {

    private val _name = MutableLiveData("Kim John")
    private val _likes = MutableLiveData(0)


    val name :LiveData<String> = _name

    val likes :LiveData<Int> = _likes

    fun onLike(){
        _likes.value = (likes.value?:0)+1
    }
    // 声明 popularity 并通过 Transformations 创建其与 likes 的关系
    val popularity:LiveData<Popularity> = Transformations.map(_likes){
        when {
            it > 9 -> Popularity.STAR
            it > 5 -> Popularity.POPULAR
            else -> Popularity.NORMAL
        }
    }


    enum class Popularity  {
        NORMAL,
        POPULAR,
        STAR
    }

}