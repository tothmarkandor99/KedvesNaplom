package com.webtic.kedvesnaplom.di

import com.webtic.kedvesnaplom.network.BejegyzesService
import com.webtic.kedvesnaplom.persistence.BejegyzesDao
import com.webtic.kedvesnaplom.ui.main.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideMainRepository(
        bejegyzesService: BejegyzesService,
        posterDao: BejegyzesDao
    ): MainRepository {
        return MainRepository(bejegyzesService, posterDao)
    }
}