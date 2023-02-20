package com.example.unitconvert.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconvert.R
import kotlinx.coroutines.scheduling.DefaultIoScheduler.default


@Preview
@Composable
fun DistanceConverter() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val distance = rememberSaveable { mutableStateOf("") }
        val result = rememberSaveable { mutableStateOf("") }
        val selectedId = rememberSaveable { mutableStateOf(0) }
        DistanceTextField(distance)
        DistanceScaleButtonGroup()
        Button(onClick = {
            if (distance.value.isNotEmpty()) {
                val dis = distance.value.let {
                    try {
                        it.toFloat()
                    } catch (e: NumberFormatException) {
                        Float.NaN
                    }
                }.let {
                    if (!it.isNaN()) {
                        when (selectedId.value) {
                            R.string.meter -> it * 0.00062137F
                            R.string.mile -> it / 0.00062137F
                            else -> Float.NaN
                        }
                    } else Float.NaN
                }
            }
        }, enabled = true) {
            Text(text = stringResource(id = R.string.convert))
        }
        if (result.value.isNotEmpty()) {
            Text(
                text = result.value,
                style = MaterialTheme.typography.h3
            )
        }


    }
}

@Composable
fun DistanceTextField(distance: MutableState<String>) {

    val localFocusManager = LocalFocusManager.current
    TextField(
        value = distance.value,
        onValueChange = {
            distance.value = it
        },
        placeholder = { Text(text = stringResource(id = R.string.placeholder_distance)) },
        keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
        singleLine = true
    )
}

@Preview
@Composable
fun DistanceScaleButtonGroup() {
    val sel = rememberSaveable { mutableStateOf(0) }
    Row {
        DistanceRadioButton(
            selected = sel.value == R.string.meter,
            selectedId = sel,
            resId = R.string.meter
        )
        DistanceRadioButton(
            selected = sel.value == R.string.mile,
            selectedId = sel,
            resId = R.string.mile
        )
    }
}

@Composable
fun DistanceRadioButton(selected: Boolean, selectedId: MutableState<Int>, resId: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = { selectedId.value = resId })
        Text(text = stringResource(id = resId), modifier = Modifier.padding(start = 8.dp))
    }
}