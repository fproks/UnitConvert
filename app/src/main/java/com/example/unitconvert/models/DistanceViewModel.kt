package com.example.unitconvert.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unitconvert.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class DistanceViewModel @Inject constructor(private val state: DistanceState) : ViewModel() {

    //private val _distance = MutableLiveData<String>("")
    val distance: LiveData<String>
        get() = state.distance

    fun setDistance(distance: String) {
        state.distance.value = distance
    }

    //private val _selectedId = MutableLiveData<Int>(0)

    val selectedId: LiveData<Int>
        get() = state.selectedId

    fun setSelectedId(selectedId: Int) {
        state.selectedId.value = selectedId
    }

    val result: LiveData<String>
        get() = state.result

    fun updateResult(result: String) {
        state.result.value = result
    }

    fun getDistanceAsFloat(): Float = (distance.value ?: "").let {
        try {
            it.toFloat()
        } catch (e: NumberFormatException) {
            Float.NaN
        }
    }

    fun convert(): Float = getDistanceAsFloat().let {
        if (!it.isNaN())
            when (selectedId.value) {
                R.string.meter -> it * 0.00062137F
                R.string.mile -> it / 0.00062137F
                else -> Float.NaN
            } else Float.NaN
    }

}

@Singleton
class DistanceState @Inject constructor() {
    val distance = MutableLiveData<String>("")
    val selectedId = MutableLiveData<Int>(0)
    val result = MutableLiveData<String>("")
}