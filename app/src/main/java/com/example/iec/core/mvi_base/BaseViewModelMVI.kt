package com.example.iec.core.mvi_base

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModelMVI<State : Reducer.ViewState, Event : Reducer.ViewEvent, Effect : Reducer.ViewEffect>(
    initialState: State,
    private val reducer: Reducer<State, Event, Effect>
) {
    private val _state: MutableStateFlow<State> = MutableStateFlow<State>(initialState)
    val state = _state.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect: Channel<Effect> = Channel(Channel.CONFLATED)
    val effect = _effect.receiveAsFlow()

    init {
        _state.tryEmit(initialState)
    }

    fun absorbEvent(event: Event) {
        val (newState, _) = reducer.reduce(_state.value, event)
        _state.tryEmit(newState)
    }

    fun sendEffect(effect: Effect){
        _effect.trySend(effect)
    }

    fun absorbEventWithEffect(event: Event){
        val (newState, effect) = reducer.reduce(_state.value, event)
        _state.tryEmit(newState)
        effect?.let{
            sendEffect(it)
        }
    }
}