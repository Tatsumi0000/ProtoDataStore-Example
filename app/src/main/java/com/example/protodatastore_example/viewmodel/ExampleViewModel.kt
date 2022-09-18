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
        val result: Int
    )

    private val _uiState = MutableStateFlow(UiState(0))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun write(result: Int) {
        viewModelScope.launch {
            repository.write(result)
        }
    }

    fun read() {
        viewModelScope.launch {
            val data = repository.read()
            _uiState.update {
                it.copy(result = data.first().result)
            }
        }
    }
}