package org.cipo.calcu_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EntryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: EntryRepository
    val allEntries: LiveData<List<Entry>>

    init {
        val entryDao = EntryRoomDatabase.getDatabase(application, viewModelScope).entryDao()
        repository = EntryRepository(entryDao)
        allEntries = repository.allEntries
    }

    fun insert(entry: Entry) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(entry)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }


}