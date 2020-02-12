package com.albuquerque.starwarswiki.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.albuquerque.starwarswiki.core.utils.ListConverter
import com.albuquerque.starwarswiki.app.dao.WikiDAO
import com.albuquerque.starwarswiki.app.model.entity.ConfigEntity
import com.albuquerque.starwarswiki.app.model.entity.PersonEntity

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        PersonEntity::class,
        ConfigEntity::class
    ]
)
@TypeConverters(ListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "starwarswiki"

        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            return instance
                ?: synchronized(this) {
                    instance ?: synchronized(this) {
                        buildDatabase(
                            context
                        )
                    }
                }
        }

        private fun buildDatabase(context: Context): AppDatabase {

            return Room.databaseBuilder(
                context, AppDatabase::class.java,
                DATABASE_NAME
            )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
        }

    }

    abstract val wikiDAO: WikiDAO

}