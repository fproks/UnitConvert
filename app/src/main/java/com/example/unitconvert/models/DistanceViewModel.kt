package com.example.unitconvert.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unitconvert.R

class DistanceViewModel : ViewModel() {

    private val _distance = MutableLiveData<String>("")
    val distance: LiveData<String>
        get() = _distance

    fun setDistance(distance: String) {
        _distance.value = distance
    }

    private val _selectedId = MutableLiveData<Int>(0)

    val selectedId: LiveData<Int>
        get() = _selectedId

    fun setSelectedId(selectedId: Int) {
        _selectedId.value = selectedId
    }

    fun getDistanceAsFloat(): Float = (_distance.value ?: "").let {
        try {
            it.toFloat()
        } catch (e: NumberFormatException) {
            Float.NaN
        }
    }

    fun convert(): Float = getDistanceAsFloat().let {
        if (!it.isNaN())
            when (_selectedId.value) {
                R.string.meter -> it * 0.00062137F
                R.string.mile -> it / 0.00062137F
                else -> Float.NaN
            } else Float.NaN
    }

}