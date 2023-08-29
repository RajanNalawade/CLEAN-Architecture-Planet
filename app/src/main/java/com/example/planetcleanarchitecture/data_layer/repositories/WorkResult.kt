package com.example.planetcleanarchitecture.data_layer.repositories

sealed class WorkResult<out K> {
    data class Success<out R>(val data: R) : WorkResult<R>()
    data class Error(val exception: Exception) : WorkResult<Nothing>()
    object Loading : WorkResult<Nothing>()
}
