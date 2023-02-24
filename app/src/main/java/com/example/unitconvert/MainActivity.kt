package com.example.unitconvert

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.unitconvert.compose.ComposeUnitConverter
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class UnitConvertActivity : Application()

@AndroidEntryPoint
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






