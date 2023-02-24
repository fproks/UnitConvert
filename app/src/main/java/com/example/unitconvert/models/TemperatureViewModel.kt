package com.example.unitconvert.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.unitconvert.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Singleton

/*
* viewModel 注入
*
*
* */

@HiltViewModel
class TemperatureViewModel @Inject constructor(private val state: TemperatureState) : ViewModel() {
    //private val _scale: MutableLiveData<Int> = MutableLiveData(R.string.celsius)

    val scale: LiveData<Int>
        get() = state.scale

    fun setScale(value: Int) {
        state.scale.value = value
    }

    //private val _temperature: MutableLiveData<String> = MutableLiveData("")

    val temperature: LiveData<String>
        get() = state.temperature

    fun setTemperature(value: String) {
        state.temperature.value = value
    }


    val result: LiveData<String>
        get() = state.result

    fun setResult(value: String) {
        state.result.value = value
    }


    private fun getTemperatureAsFloat(): Float = (temperature.value ?: "").let {
        return try {
            it.toFloat()
        } catch (e: NumberFormatException) {
            Float.NaN
        }
    }

    fun convert() = getTemperatureAsFloat().let {
        if (!it.isNaN())
            if (scale.value == R.string.celsius)
                (it * 1.8F) + 32F
            else
                (it - 32F) / 1.8F
        else
            Float.NaN
    }
}

@Singleton
class TemperatureState @Inject constructor() {
    val scale: MutableLiveData<Int> = MutableLiveData(R.string.celsius)
    val temperature: MutableLiveData<String> = MutableLiveData("")
    val result: MutableLiveData<String> = MutableLiveData("")
}