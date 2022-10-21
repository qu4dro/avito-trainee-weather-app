package dev.orlov.weather.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.orlov.weather.data.db.entity.CityEntity

@Database(entities = [CityEntity::class], exportSchema = false, version = 1)
abstract class CitiesDatabase : RoomDatabase() {

    abstract fun getCitiesDao(): CitiesDao

    companion object {

        private const val DATABASE_NAME = "cities_db.db"

        @Volatile
        private var instance: CitiesDatabase? = null

        fun getInstance(context: Context): CitiesDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): CitiesDatabase {
            val database =
                Room.databaseBuilder(context, CitiesDatabase::class.java, DATABASE_NAME)
                    .addCallback(object : RoomDatabase.Callback() {})
                    .build()
            return database
        }

    }

}