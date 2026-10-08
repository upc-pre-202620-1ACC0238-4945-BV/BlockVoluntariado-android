package com.example.blockvoluntariado.feature.discoveryVolunteering.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blockvoluntariado.feature.discoveryVolunteering.application.GetConvocatoriaUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val getConvocatorias: GetConvocatoriaUseCase): ViewModel(){

    private val _state = MutableStateFlow(HomeUiState()) // traemos estados
    val state: StateFlow<HomeUiState> = _state.asStateFlow() // mostramos los estadps

    fun loadConvocatorias(){


        viewModelScope.launch(Dispatchers.IO){

            _state.update { currentState->
                currentState.copy(isLoading = true)

            }

            val resultado = getConvocatorias()

            resultado.fold(
                onSuccess = { convocatorias ->
                    _state.update { currentState ->
                        currentState.copy(convocatorias = convocatorias, isLoading = false)
                    }
                },

                onFailure = { error ->
                    _state.update { currentState ->
                        currentState.copy(errorMessage = error.message, isLoading = false)
                    }
                }
            )
        }
    }



    init {
        loadConvocatorias()
    }

}