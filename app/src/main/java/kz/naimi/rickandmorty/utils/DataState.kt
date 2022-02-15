package kz.naimi.rickandmorty.utils

import kz.naimi.rickandmorty.network.models.Character

sealed class DataState {
    data class Error(val message: String) : DataState()
    data class Success(val characters: List<Character>) : DataState()
    object Progress : DataState()
}