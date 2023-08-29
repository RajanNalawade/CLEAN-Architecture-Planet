package com.example.planetcleanarchitecture.di

import android.content.Context
import androidx.room.Room
import com.example.planetcleanarchitecture.data_layer.data_sources.local.LocalDataSource
import com.example.planetcleanarchitecture.data_layer.data_sources.local.PlanetsDatabase
import com.example.planetcleanarchitecture.data_layer.data_sources.local.RoomLocalDataSource
import com.example.planetcleanarchitecture.data_layer.data_sources.remote.ApiRemoteDataSource
import com.example.planetcleanarchitecture.data_layer.data_sources.remote.RemoteDataSource
import com.example.planetcleanarchitecture.data_layer.repositories.PlanetsRepository
import com.example.planetcleanarchitecture.data_layer.repositories.PlanetsRepositoryImplementation
import com.example.planetcleanarchitecture.domain_layer.AddPlanetUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideAddPlanetUseCase(
        repository: PlanetsRepository
    ): AddPlanetUseCase {
        return AddPlanetUseCase(repository)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePlanetsRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): PlanetsRepository {
        return PlanetsRepositoryImplementation(localDataSource, remoteDataSource)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideRemoteDataSource(): RemoteDataSource {
        return ApiRemoteDataSource()
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(
        database: PlanetsDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): LocalDataSource {
        return RoomLocalDataSource(database.planetsDao(), ioDispatcher)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PlanetsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            PlanetsDatabase::class.java,
            "Planets.db"
        ).build()
    }
}