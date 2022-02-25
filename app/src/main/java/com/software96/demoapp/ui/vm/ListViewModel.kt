package com.software96.demoapp.ui.vm

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.software96.demoapp.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val _repository: Repository,
    private val _savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        private const val DEFAULT_QUERY = "language:Kotlin"
    }

    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val trendListResponse = currentQuery.switchMap { queryString ->
        _repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchRepos(query: String) {
        currentQuery.value = query
    }
}