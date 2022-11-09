package com.iulian.iancu.cocktailapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iulian.iancu.data.Drinks
import com.iulian.iancu.data.DrinksRepository
import kotlinx.coroutines.*

class MainViewModel constructor(
    //TODO Inject with dagger instead of this nonsense
    private val drinksRepository: DrinksRepository
) : ViewModel() {
    private val _state = MutableLiveData(State(null, null))
    val state: LiveData<State> get() = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _state.postValue(_state.value?.copy(error = Error.Unknown))
    }
    private var job: Job? = null


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun getDrinks() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = drinksRepository.getDrinks()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful && !response.body()?.drinks.isNullOrEmpty()) {
                    val drinks = response.body()
                    _state.postValue(
                        _state.value?.copy(
                            error = null,
                            drinks = drinks
                        )
                    )
                } else {
                    _state.postValue(_state.value?.copy(error = Error.Network))
                }
            }
        }
    }
}


data class State(
    val error: Error?,
    val drinks: Drinks?
)

sealed class Error {
    object Network : Error()
    object Unknown : Error()
}