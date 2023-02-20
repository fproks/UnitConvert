package com.example.unitconvert.compose

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconvert.R

@Preview
@Composable
fun TemperatureConverter() {
    val fahrenheit = stringResource(id = R.string.fahrenheit)
    val celsius = stringResource(id = R.string.celsius)
    var result by rememberSaveable { mutableStateOf("") }
    val selectedId = rememberSaveable { mutableStateOf(0) }
    val temperature = rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TemperatureTextField(temperature)
        TemperatureScaleButtonGroup(selectedId)
        Button(onClick = {
            val tmp = temperature.value.let {
                try {
                    it.toFloat()
                } catch (e: NumberFormatException) {
                    Float.NaN
                }
            }.let {
                if (!it.isNaN()) {
                    if (selectedId.value == R.string.celsius) (it * 1.8F) + 32F
                    else (it - 32F) / 1.8F
                } else Float.NaN
            }
            result = if (!tmp.isNaN()) "$tmp${
                if (selectedId.value == R.string.celsius) fahrenheit
                else celsius
            }" else ""

        }, enabled = true) {
            Text(text = stringResource(id = R.string.convert))
        }
        if (result.isNotEmpty()) {
            Text(text = result)
        }

    }
}


//少一个隐藏键盘

@Composable
fun TemperatureTextField(temperature: MutableState<String>) {
    val localFocus = LocalFocusManager.current
    val context = LocalContext.current
    TextField(
        value = temperature.value,
        onValueChange = { temperature.value = it },
        placeholder = { Text(text = stringResource(id = R.string.placeholder)) },
        keyboardActions = KeyboardActions(onDone = {
            localFocus.clearFocus()
            Toast.makeText(context, "you have input ${temperature.value}", Toast.LENGTH_SHORT).show()
        }),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
        ),
        singleLine = true
    )
}


@Composable
fun TemperatureScaleButtonGroup(selectedId: MutableState<Int>) {

    Row {
        TemperatureRadioButton(selected = selectedId.value == R.string.celsius, selectedId, R.string.celsius)
        TemperatureRadioButton(selected = selectedId.value == R.string.fahrenheit, selectedId, R.string.fahrenheit)
    }
}

@Composable
fun TemperatureRadioButton(selected: Boolean, selectedId: MutableState<Int>, resId: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = {
            selectedId.value = resId
        })
        Text(text = stringResource(resId), modifier = Modifier.padding(start = 8.dp))
    }
}
