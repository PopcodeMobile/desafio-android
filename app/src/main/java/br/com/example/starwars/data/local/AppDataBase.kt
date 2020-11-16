package br.com.example.starwars.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.example.starwars.data.entities.ApiPeople
import br.com.example.starwars.data.entities.RemoteKeys
import br.com.example.starwars.data.util.StringListConverter

@Database(entities = [ApiPeople::class, RemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun peopleDao(): PeopleDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        private const val DATABASE_NAME = "star-wars-db"

        fun build(context: Context): AppDataBase {
            return Room.databaseBuilder(
                context,
                AppDataBase::class.java, DATABASE_NAME
            ).build()
        }
    }
}