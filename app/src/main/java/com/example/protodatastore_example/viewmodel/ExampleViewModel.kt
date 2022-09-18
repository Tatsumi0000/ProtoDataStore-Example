package com.example.protodatastore_example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.protodatastore_example.repository.ExampleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val repository: ExampleRepository
): ViewModel() {
    data class UiState(
        val result: Int,
        val isEnabledReadButton: Boolean = true,
        val isEnabledWriteButton: Boolean = true
    )

    private val _uiState = MutableStateFlow(UiState(0))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun write(result: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isEnabledWriteButton = false) }
            repository.write(result)
            _uiState.update { it.copy(isEnabledWriteButton = true) }
        }
    }

    fun read() {
        viewModelScope.launch {
            _uiState.update { it.copy(isEnabledReadButton = false) }
            val data = repository.read()
            _uiState.update { it.copy(result = data.first().result, isEnabledReadButton = true) }
        }
    }
}