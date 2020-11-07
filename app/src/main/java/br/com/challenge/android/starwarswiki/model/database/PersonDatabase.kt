package br.com.challenge.android.starwarswiki.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PersonEntity::class],
    version = 1,
    exportSchema = false)
abstract class PersonDatabase: RoomDatabase() {
    /**
     * Connects the database to the DAO.
     */
    abstract val personDatabaseDao: PersonDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: PersonDatabase? = null

        fun getInstance(context: Context): PersonDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PersonDatabase::class.java,
                        "person_history_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE = instance
                }

                return instance
            }
        }

    }

}