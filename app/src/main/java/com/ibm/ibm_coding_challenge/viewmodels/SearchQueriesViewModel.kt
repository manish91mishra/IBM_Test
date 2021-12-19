package com.ibm.ibm_coding_challenge.viewmodels

import androidx.lifecycle.*
import com.ibm.ibm_coding_challenge.model.db.LocRepository
import com.ibm.ibm_coding_challenge.model.db.LocationEntity
import com.ibm.ibm_coding_challenge.utils.Utils

class SearchQueriesViewModel(val locRepository: LocRepository) : ViewModel() {

    var searchList: LiveData<List<LocationEntity>> = locRepository.allQueriesList.asLiveData()

}
class SearchQueriesViewModelFactory(private val repository: LocRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchQueriesViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return SearchQueriesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}