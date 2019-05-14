package org.cipo.calcu_list

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

    @Query("DELETE FROM entry_table")
    fun deleteAll()
}