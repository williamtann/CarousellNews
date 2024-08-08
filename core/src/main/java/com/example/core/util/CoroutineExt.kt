package com.example.core.util

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun CoroutineScope.safeLaunch(
    block: suspend CoroutineScope.() -> Unit,
    onError: (e: Exception) -> Unit = {},
    onFinish: () -> Unit = {}
): Job = launch {
    try {
        block()
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        onError(e)
    } finally {
        onFinish()
    }
}