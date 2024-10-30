package com.example.em_mattress.NavAndViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.em_mattress.CSVfunctions.Order

class SharedViewModel: ViewModel() {
    var orderArray by mutableStateOf<List<Order>>(mutableListOf<Order>())
        private set

    fun updateOrderArray(neworderArray: List<Order>) {
        orderArray = neworderArray
    }
}