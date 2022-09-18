package com.example.protodatastore_example.di

import com.example.protodatastore_example.repository.ExampleRepository
import com.example.protodatastore_example.repository.ExampleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindExampleRepository(repository: ExampleRepositoryImpl): ExampleRepository
}