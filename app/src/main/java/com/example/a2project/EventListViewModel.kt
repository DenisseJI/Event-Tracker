package com.example.a2project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class EventListViewModel : ViewModel() {
    private val eventRepository = EventRepository.get()

    private val _events: MutableStateFlow<List<Event>> = MutableStateFlow(emptyList())

    val events: StateFlow<List<Event>>
    get() = _events.asStateFlow()

    init {
        viewModelScope.launch {
            eventRepository.getEvents().collect{
                _events.value = it
            }

        }
    }
    suspend fun addEvent(event: Event) {
        eventRepository.addEvent(event)
    }

}