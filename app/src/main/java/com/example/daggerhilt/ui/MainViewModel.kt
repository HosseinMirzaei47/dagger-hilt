package com.example.daggerhilt.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.daggerhilt.model.BlogView
import com.example.daggerhilt.repository.MainRepository
import com.example.daggerhilt.util.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<BlogView>>> = MutableLiveData()
    val dataState: LiveData<DataState<List<BlogView>>> get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is MainStateEvent.GetBlogsEvent -> {
                    mainRepository.getBlog()
                        .onEach { dataState ->
                            _dataState.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                is MainStateEvent.NoneEvent -> {
                    /*Who cares*/
                }
            }
        }
    }

}

sealed class MainStateEvent {

    object GetBlogsEvent : MainStateEvent()

    object NoneEvent : MainStateEvent()

}