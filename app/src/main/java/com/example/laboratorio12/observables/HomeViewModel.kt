package com.example.laboratorio12.observables

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.Started)
    val state: StateFlow<HomeState> = _state
    private lateinit var job: Job

    sealed class HomeState {
        object Started: HomeState()
        class Default(val messageDefault: String): HomeState()
        class Success(val messageSuccess: String): HomeState()
        class Failure(val messageFailure: String): HomeState()
        class Empty(val messageEmpty: String): HomeState()
    }

    fun doDefault(){
        viewModelScope.launch {
            _state.value = HomeState.Default(
                messageDefault = "Selecciona una opción"
            )
        }
    }

    fun doSuccess(){
        viewModelScope.launch {
            _state.value = HomeState.Success(
                messageSuccess = "¡Operación exitosa!"
            )
        }
    }

    fun doFailure(){
        viewModelScope.launch {
            _state.value = HomeState.Failure(
                messageFailure = "¡Operación fallida!"
            )
        }
    }

    fun doEmpty(){
        viewModelScope.launch {
            _state.value = HomeState.Empty(
                messageEmpty = "Sin resultados"
            )
        }
    }
}