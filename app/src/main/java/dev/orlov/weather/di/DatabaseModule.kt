package dev.orlov.weather.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.orlov.weather.data.db.CitiesDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCitiesDatabase(@ApplicationContext context: Context) =
        CitiesDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideCitiesDao(db: CitiesDatabase) = db.getCitiesDao()

}