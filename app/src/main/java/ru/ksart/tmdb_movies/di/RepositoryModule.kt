package ru.ksart.tmdb_movies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ru.ksart.tmdb_movies.data.repository.MoviesRepositoryImpl
import ru.ksart.tmdb_movies.data.repository.SettingsRepositoryImpl
import ru.ksart.tmdb_movies.domain.repository.MoviesRepository
import ru.ksart.tmdb_movies.domain.repository.SettingsRepository

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    @ViewModelScoped
    fun provideMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository

    @Binds
    @ViewModelScoped
    fun provideSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository
}
