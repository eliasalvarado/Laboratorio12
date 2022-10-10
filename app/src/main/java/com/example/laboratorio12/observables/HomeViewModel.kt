package com.example.laboratorio12.observables

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.Started)
    val state: StateFlow<HomeState> = _state

    sealed class HomeState {
        object Started: HomeState()
        class Default(val messageDefault: String): HomeState()
        class Success(val messageSuccess: String): HomeState()
        class Failure(val messageFailure: String): HomeState()
        class Empty(val messageEmpy: String): HomeState()
    }

    fun doDefault(){
        viewModelScope.launch {
            _state.value = HomeState.Default(
                messageDefault = "Selecciona una opción"
            )
        }
    }
}