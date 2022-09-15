package io.github.sovathna.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<S : Any>(initState: S) {
    private val states = MutableStateFlow(initState)
    val statesFlow: StateFlow<S> = states
    val current get() = states.value

    protected fun setState(state: S) {
        states.value = state
    }
}