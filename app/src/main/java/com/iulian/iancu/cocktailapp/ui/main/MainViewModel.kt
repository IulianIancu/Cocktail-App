package com.iulian.iancu.cocktailapp.ui.main

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iulian.iancu.domain.GetDrinksUseCase
import com.iulian.iancu.entity.Cocktail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getDrinksUseCase: GetDrinksUseCase
) : ViewModel() {
    private val _state = MutableLiveData(State(null, null))
    val state: LiveData<State> get() = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        if (error is NetworkErrorException)
            _state.postValue(_state.value?.copy(error = Error.Network))
        else
            _state.postValue(_state.value?.copy(error = Error.Unknown))
    }
    private var job: Job? = null


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun getDrinks(search: String = "") {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = getDrinksUseCase(search)

            withContext(Dispatchers.Main) {
                _state.postValue(
                    _state.value?.copy(
                        error = null,
                        drinks = response
                    )
                )
            }
        }
    }
}


data class State(
    val error: Error?,
    val drinks: List<Cocktail>?
)

sealed class Error {
    object Network : Error()
    object Unknown : Error()
}