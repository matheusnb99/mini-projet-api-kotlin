package com.matheusborba.projetkotlin.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.thread

class RecipesViewModel: ViewModel() {
    val data: MutableLiveData<RecipeBean?> = MutableLiveData()
    val errorMessage: MutableLiveData<String?> = MutableLiveData()
    val threadRuning: MutableLiveData<Boolean> = MutableLiveData()


    fun loadData() {
        //reset des données
        threadRuning.postValue(true)
        errorMessage.postValue(null)
        data.postValue(null)

        thread {
            try {
                data.postValue(RequestUtils.loadRecipeApi("tomato%20soup"))
            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage.postValue(e.message)
            }
            threadRuning.postValue(false)

        }
    }
}