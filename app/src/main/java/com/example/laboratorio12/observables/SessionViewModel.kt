package com.example.laboratorio12.observables

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SessionViewModel: ViewModel() {
    private val _validAuthToken: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val stateFlow: StateFlow<Boolean> = _validAuthToken

    fun triggerStateFlow() {
        _validAuthToken.value = true

        viewModelScope.launch {
            delay(30000L)
            _validAuthToken.value = false
        }
    }
}