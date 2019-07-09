package org.cipo.calcu_list.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry_table")
class Entry(
    @field:ColumnInfo(name = "word")
    var word: String?,
    @field:ColumnInfo(name = "value")
    var value: Long?,
    @field:ColumnInfo(name = "selected")
    var selected: Boolean? = false
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}