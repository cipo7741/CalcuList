package org.cipo.calcu_list

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry_table")
class Entry(
    @field:ColumnInfo(name = "word")
    var word: String?,
    @field:ColumnInfo(name = "value")
    var value: Int?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}