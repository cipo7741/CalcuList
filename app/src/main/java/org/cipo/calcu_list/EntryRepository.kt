package org.cipo.calcu_list

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class EntryRepository(private val wordDao: EntryDao) {

    val allEntries: LiveData<List<Entry>> = wordDao.getAllEntries()

    @WorkerThread
    suspend fun insert(entry: Entry) {
        wordDao.insert(entry)
    }

    @WorkerThread
    suspend fun deleteAll() {
        wordDao.deleteAll()
    }

}