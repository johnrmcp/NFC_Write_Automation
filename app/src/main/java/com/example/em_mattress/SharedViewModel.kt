package com.example.em_mattress

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    var orderArray by mutableStateOf<List<Order>>(mutableListOf<Order>())

    fun updateOrderArray(neworderArray: List<Order>) {
        orderArray = neworderArray
    }
}