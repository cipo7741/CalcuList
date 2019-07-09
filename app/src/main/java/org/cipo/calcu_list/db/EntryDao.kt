package org.cipo.calcu_list.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EntryDao {

    @Query("SELECT * from entry_table")
    fun getAllEntries(): LiveData<List<Entry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: Entry)

    @Query("DELETE FROM entry_table where id =:entryID")
    fun delete(entryID: Int)

    @Query("DELETE FROM entry_table where selected = 1")
    fun deleteSelected()

    @Query("DELETE FROM entry_table")
    fun deleteAll()

    @Query("UPDATE entry_table SET selected = NOT selected where id =:entryId")
    fun updateSelected(entryId: Int)

    @Query("UPDATE entry_table SET selected = 1 where selected != 1")
    fun updateSelectedAllTrue()

    @Query("UPDATE entry_table SET selected = NOT selected")
    fun updateSelectedAll()

    @Query("SELECT COUNT(selected) FROM entry_table where selected = 1")
    fun countSelected() : LiveData<Int>

    @Query("SELECT SUM(value) FROM entry_table")
    fun getSumOfValues(): LiveData<Long>


}