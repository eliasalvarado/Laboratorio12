package com.example.laboratorio12.observables

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SessionViewModel: ViewModel() {
    private val _validAuthToken: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val stateFlow: StateFlow<Boolean> = _validAuthToken
    private lateinit var job: Job

    fun triggerStateFlow(correo: String, password: String): Boolean {
        return if(correo == password && correo.isNotEmpty() && password.isNotEmpty() && correo == "alv21808@uvg.edu.gt"){
            _validAuthToken.value = true

            job = viewModelScope.launch {
                delay(30000L)
                _validAuthToken.value = false
            }
            true
        } else false
    }

    fun mantenerSesion() {
        if (this::job.isInitialized && job.isActive) {
            job.cancel()
            _validAuthToken.value = true
        }
    }

    fun cerrarSesion() {
        _validAuthToken.value = false
    }
}