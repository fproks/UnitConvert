package com.example.unitconvert.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unitconvert.R
import com.example.unitconvert.models.DistanceViewModel
import kotlinx.coroutines.launch


@Preview
@Composable
fun DistanceConverter() {
    val meter = stringResource(id = R.string.meter)
    val mile = stringResource(id = R.string.mile)
    val viewModel: DistanceViewModel = viewModel()
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //val distance = rememberSaveable { mutableStateOf("") }
        val distance = viewModel.distance.observeAsState(viewModel.distance.value ?: "")
        val selectedId = viewModel.selectedId.observeAsState(viewModel.selectedId.value ?: 0)
        var result by rememberSaveable { mutableStateOf("") }
        val scope = rememberCoroutineScope()
        // val selectedId = rememberSaveable { mutableStateOf(0) }
        DistanceTextField(distance) { viewModel.setDistance(it) }
        DistanceScaleButtonGroup(selectedId) { viewModel.setSelectedId(it) }
        Button(onClick = {
            scope.launch {
                val dis = viewModel.convert()
                result = if (!dis.isNaN()) "$dis${
                    if (selectedId.value == R.string.meter) meter
                    else mile
                }" else ""
            }
        }, enabled = true) {
            Text(text = stringResource(id = R.string.convert))
        }
        if (result.isNotEmpty()) {
            Text(
                text = result,
                style = MaterialTheme.typography.h3
            )
        }


    }
}

@Composable
fun DistanceTextField(distance: State<String>, onValueChange: (String) -> Unit) {

    val localFocusManager = LocalFocusManager.current
    TextField(
        value = distance.value,
        onValueChange = {
            onValueChange(it)
        },
        placeholder = { Text(text = stringResource(id = R.string.placeholder_distance)) },
        keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
        singleLine = true
    )
}


@Composable
fun DistanceScaleButtonGroup(sel: State<Int>, onSelected: (Int) -> Unit) {
    Row {
        DistanceRadioButton(
            selected = sel.value == R.string.meter,
            resId = R.string.meter,
            onSelected
        )
        DistanceRadioButton(
            selected = sel.value == R.string.mile,
            resId = R.string.mile,
            onSelected
        )
    }
}

@Composable
fun DistanceRadioButton(selected: Boolean, resId: Int, onSelected: (Int) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = { onSelected(resId) })
        Text(text = stringResource(id = resId), modifier = Modifier.padding(start = 8.dp))
    }
}