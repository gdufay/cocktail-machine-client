package com.example.cocktailmachine.di

import android.content.Context
import androidx.room.Room
import com.example.cocktailmachine.data.CocktailDao
import com.example.cocktailmachine.data.CocktailDatabase
import com.example.cocktailmachine.data.IngredientDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): CocktailDatabase {
        return Room.databaseBuilder(
            appContext,
            CocktailDatabase::class.java,
            "cocktail_database"
        ).build()
    }

    @Provides
    fun provideCocktailDao(database: CocktailDatabase): CocktailDao {
        return database.cocktailDao()
    }

    @Provides
    fun provideIngredientDao(database: CocktailDatabase): IngredientDao {
        return database.ingredientDao()
    }
}