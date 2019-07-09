package org.cipo.calcu_list

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import org.cipo.calcu_list.db.Entry
import org.cipo.calcu_list.db.EntryDao

class EntryRepository(private val wordDao: EntryDao) {

    val allEntries: LiveData<List<Entry>> = wordDao.getAllEntries()
    val countSelected: LiveData<Int> = wordDao.countSelected()
    val total: LiveData<Long> = wordDao.getSumOfValues()

    @WorkerThread
    suspend fun insert(entry: Entry) {
        wordDao.insert(entry)
    }

    @WorkerThread
    fun delete(entry: Entry) {
        wordDao.delete(entry.id)
    }

    @WorkerThread
    fun deleteAll() {
        wordDao.deleteAll()
    }

    @WorkerThread
    fun updateSelected(entryId: Int) {
        wordDao.updateSelected(entryId)
    }

    @WorkerThread
    fun deleteSelected() {
        wordDao.deleteSelected()
    }

    @WorkerThread
    fun updateSelectedAll() {
        wordDao.updateSelectedAll()
    }

    @WorkerThread
    fun updateSelectedAllTrue() {
        wordDao.updateSelectedAllTrue()
    }

}