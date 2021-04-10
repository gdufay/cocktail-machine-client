package com.example.cocktailmachine.di

import android.content.Context
import androidx.room.Room
import com.example.cocktailmachine.data.CocktailDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context,
        callback: CocktailDatabase.Callback
    ) = Room.databaseBuilder(appContext, CocktailDatabase::class.java, "cocktail_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    fun provideCocktailDao(database: CocktailDatabase) = database.cocktailDao()

    @Provides
    fun provideIngredientDao(database: CocktailDatabase) = database.ingredientDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope