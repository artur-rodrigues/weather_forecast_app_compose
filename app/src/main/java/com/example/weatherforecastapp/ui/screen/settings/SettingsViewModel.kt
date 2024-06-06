package com.example.weatherforecastapp.ui.screen.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapp.data.model.Result
import com.example.weatherforecastapp.domain.model.Unit
import com.example.weatherforecastapp.domain.usecase.GetUnitsUseCase
import com.example.weatherforecastapp.domain.usecase.InsertUnitUseCase
import com.example.weatherforecastapp.domain.usecase.RemoveUnitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getUseCase: GetUnitsUseCase,
    private val insertUseCase: InsertUnitUseCase,
    private val removeUseCase: RemoveUnitUseCase,
) : ViewModel() {

    private val _unitResult = MutableStateFlow<Result<List<Unit>>>(Result.Loading)
    val unitResult = _unitResult.asStateFlow()

    private val _unitInsertResult = MutableLiveData<kotlin.Unit>()
    val unitInsertResult: LiveData<kotlin.Unit> = _unitInsertResult

    init {
        getAllUnits()
    }

    private fun getAllUnits() {
        viewModelScope.launch {
            getUseCase.get().distinctUntilChanged()
                .collect {
                    _unitResult.value = it
                }
        }
    }

    fun insertUnit(unit: Unit) {
        viewModelScope.launch {
            insertUseCase.add(unit)
            _unitInsertResult.value = Unit
        }
    }

    fun updateUnit(unit: Unit) {
        viewModelScope.launch {
            insertUseCase.update(unit)
        }
    }

    fun removeUnit(unit: Unit) {
        viewModelScope.launch {
            removeUseCase.remove(unit)
        }
    }

    fun removeAllUnit() {
        viewModelScope.launch {
            removeUseCase.removeAll()
        }
    }
}