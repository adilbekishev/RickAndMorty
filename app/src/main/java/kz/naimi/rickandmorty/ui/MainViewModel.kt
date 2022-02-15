package kz.naimi.rickandmorty.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import kz.naimi.rickandmorty.network.RetrofitInstance
import kz.naimi.rickandmorty.network.models.Character
import kz.naimi.rickandmorty.utils.Constants.QUERY_PAGE_SIZE
import kz.naimi.rickandmorty.utils.DataState

class MainViewModel : ViewModel() {
    private val _state = MutableLiveData<DataState>()
    val state = _state

    fun getCharacters(listSize: Int) =
        viewModelScope.launch {
            state.value = DataState.Progress
            val page = listSize / QUERY_PAGE_SIZE + 1
            try {
                val response = RetrofitInstance.service.getCharacters(page)
                _state.value = DataState.Success(response.characters)
            } catch (e: Exception) {
                if (e is CancellationException) {
                    throw e
                }
                Log.d("ggg", e.localizedMessage ?: "Error")
                state.value = DataState.Error(e.localizedMessage ?: "Error")
            }
        }
}