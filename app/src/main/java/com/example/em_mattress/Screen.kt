package com.example.em_mattress

sealed class Screen(val route: String) {
    object CSVRead : Screen(route= "CSV-read")
    object DataOutput : Screen(route = "data-output")
}