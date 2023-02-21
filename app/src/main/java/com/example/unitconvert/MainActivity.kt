package com.example.unitconvert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.unitconvert.compose.ComposeUnitConverter
import com.example.unitconvert.compose.DistanceConverter
import com.example.unitconvert.compose.TemperatureConverter
import com.example.unitconvert.ui.theme.UnitConvertTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
          /*  UnitConvertTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    //Greeting("Android")
                    //TemperatureConverter()
                   // DistanceConverter()
                }
            }*/
            ComposeUnitConverter()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}






