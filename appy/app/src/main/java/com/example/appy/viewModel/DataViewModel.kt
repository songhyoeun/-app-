package com.example.appy.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import com.example.appy.model.CampingData


class DataViewModel : ViewModel() {
    private val _apiData = MutableLiveData<List<CampingData>>()
    val apiData: LiveData<List<CampingData>> get() = _apiData

    fun setApiData(data: List<CampingData>) {
        _apiData.value = data
    }
}