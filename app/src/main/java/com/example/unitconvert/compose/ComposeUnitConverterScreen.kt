package com.example.unitconvert.compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.unitconvert.R

sealed class ComposeUnitConverterScreen(
    val route: String,
    @StringRes val label: Int,
    @DrawableRes val icon: Int
) {
    companion object {
        val screens = listOf(Temperature, Distance)
        const val route_temperature = "temperature"
        const val route_distances = "distances"
    }

    private object Temperature : ComposeUnitConverterScreen(
        route_temperature,
        R.string.temperature,
        R.drawable.baseline_thermostat_24
    )

    private object Distance : ComposeUnitConverterScreen(
        route_distances,
        R.string.distances,
        R.drawable.baseline_square_foot_24
    )
}
