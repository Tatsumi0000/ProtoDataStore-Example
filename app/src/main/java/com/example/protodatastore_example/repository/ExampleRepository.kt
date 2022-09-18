package com.example.protodatastore_example.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.protodatastore_example.entity.ExamplePreferences
import com.example.protodatastore_example.serializer.ExampleSerializer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

interface ExampleRepository {
    suspend fun read(): Flow<ExamplePreferences>
    suspend fun write(result: Int)
}

@Singleton
class ExampleRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): ExampleRepository {

    private val Context.dataStore: DataStore<ExamplePreferences> by dataStore(
        fileName = "EXAMPLE_DATA_STORE",
        serializer = ExampleSerializer
    )

    override suspend fun read(): Flow<ExamplePreferences> {
        try {
            return flowOf(context.dataStore.data.first())
        } catch (e: Exception) {
            return flowOf(ExamplePreferences(result = 0))
        }
    }

    override suspend fun write(result: Int) {
        context.dataStore.updateData { ExamplePreferences(result) }
    }
}