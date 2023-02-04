package com.example.cosplayyoutobe.base

import androidx.lifecycle.ViewModel

fun <T : Any> ViewModel.checkAcceptContinueRetry(
    result: Result<T>,
    retryCallback: () -> Unit,
    successCallback: () -> Unit,
    loadingCallback: (() -> Unit?)? = null
) {
    when (result) {
        is Result.InProgress -> {
            loadingCallback?.invoke()
        }
        is Result.Success -> {
            successCallback.invoke()
        }
        else -> {
            retryCallback.invoke()
        }
    }
}