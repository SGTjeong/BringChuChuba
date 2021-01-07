package com.bring.chuchuba.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<T>(protected val uiContext : CoroutineContext) : ViewModel(), CoroutineScope {
    abstract fun handleEvent(event : T)

    private var jobTracker : Job = Job()
    
    /**
     * CoroutineContext 를 오버라이드 하여 “Job + 사용할 스레드를 정의한 Dispatcher” 를 지정한다.
     */
    override val coroutineContext : CoroutineContext
            get() = jobTracker + uiContext
    
}