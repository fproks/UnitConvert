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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.unitconvert.R
import com.example.unitconvert.models.TemperatureViewModel
import kotlinx.coroutines.launch

@Preview
@Composable
fun TemperatureConverter(
     viewModel: TemperatureViewModel = hiltViewModel()
) {
        val fahrenheit = stringResource(id = R.string.fahrenheit)
    val celsius = stringResource(id = R.string.celsius)
    val temperature = viewModel.temperature.observeAsState(viewModel.temperature.value ?: "")
    val selectedId = viewModel.scale.observeAsState(viewModel.scale.value ?: 0)
    val result by viewModel.result.observeAsState(viewModel.result.value ?: "")
    val scope = rememberCoroutineScope()  //协程
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TemperatureTextField(temperature) { viewModel.setTemperature(it) }
        TemperatureScaleButtonGroup(selectedId) { viewModel.setScale(it) }
        Button(onClick = {
            scope.launch {//协程
                val tmp = viewModel.convert()
                val tmp_result = if (!tmp.isNaN()) "$tmp${
                    if (selectedId.value == R.string.celsius) fahrenheit
                    else celsius
                }" else ""
                viewModel.setResult(tmp_result)
            }
        }, enabled = true) {
            Text(text = stringResource(id = R.string.convert))
        }
        if (result.isNotEmpty()) {
            Text(text = result)
        }

    }
}


//少一个隐藏键盘
//获取数据，向上传递事件
@Composable
fun TemperatureTextField(temperature: State<String>, onValueChange: (String) -> Unit) {
    val localFocus = LocalFocusManager.current
    val context = LocalContext.current
    TextField(
        value = temperature.value,
        onValueChange = { onValueChange(it) },
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


//获取数据，向上传递事件

@Composable
fun TemperatureScaleButtonGroup(selectedId: State<Int>, onClick: (Int) -> Unit) {

    Row {
        TemperatureRadioButton(selected = selectedId.value == R.string.celsius, R.string.celsius, onClick)
        TemperatureRadioButton(selected = selectedId.value == R.string.fahrenheit, R.string.fahrenheit, onClick)
    }
}

@Composable
fun TemperatureRadioButton(selected: Boolean, resId: Int, onClick: (Int) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick =
        { onClick(resId) })
        Text(text = stringResource(resId), modifier = Modifier.padding(start = 8.dp))
    }
}
