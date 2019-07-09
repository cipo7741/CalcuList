package org.cipo.calcu_list.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Entry::class], version = 1)
abstract class EntryRoomDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao

    companion object {
        @Volatile
        private var INSTANCE: EntryRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): EntryRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                // Create database
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EntryRoomDatabase::class.java,
                    "Entry_database"
                ).addCallback(EntryDatabaseCallback(scope)).build()
                INSTANCE = instance
                instance
            }
        }

        private class EntryDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
//
//            override fun onOpen(db: SupportSQLiteDatabase) {
//                super.onOpen(db)
//                INSTANCE?.let { database ->
//                    scope.launch(Dispatchers.IO) {
//                        populateDatabase(database.entryDao())
//                    }
//                }
//            }
//
//            fun populateDatabase(entryDao: EntryDao) {
//                 Start the app with a clean database every time.
//                 Not needed if you only populate on creation.
//                entryDao.deleteAll()
//
//                var word = Entry("Hello", 1)
//                entryDao.insert(word)
//                word = Entry("World!", 2)
//                entryDao.insert(word)
//            }
        }
    }





}