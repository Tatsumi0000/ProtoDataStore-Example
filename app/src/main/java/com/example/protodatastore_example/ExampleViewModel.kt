package com.example.protodatastore_example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.protodatastore_example.repository.ExampleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val repository: ExampleRepository
): ViewModel() {
    data class UiState(
        val result: Int
    )

    private val _uiState = MutableStateFlow<UiState>(UiState(0))
    val uiState = _uiState.asStateFlow()

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