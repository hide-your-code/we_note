package com.minhdtm.example.we_note.presentations.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

open class BaseViewModel : ViewModel() {
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception.message)
        hideLoading()
        showError(exception)
    }

    private var job: Job? = null

    private var doSuspend: suspend CoroutineScope.() -> Unit = {}

    open fun showError(error: Throwable) {}

    open fun hideError() {}

    open fun showLoading() {}

    open fun hideLoading() {}

    fun doSuspend(coroutineJob: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(coroutineExceptionHandler) {
            doSuspend = coroutineJob

            job = launch {
                doSuspend.invoke(this)
            }

            job?.join()
        }
    }

    open fun retry() {
        viewModelScope.launch(coroutineExceptionHandler) {
            job?.cancel()
            job = launch {
                doSuspend.invoke(this)
            }
            job?.join()
        }
    }

    override fun onCleared() {
        job?.cancel()
        job = null
        super.onCleared()
    }
}
