package com.example.protodatastore_example.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.protodatastore_example.entity.ExamplePreferences
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Singleton

@Singleton
object ExampleSerializer: Serializer<ExamplePreferences> {

    override val defaultValue: ExamplePreferences
        get() = ExamplePreferences(result = 0)

    override suspend fun readFrom(input: InputStream): ExamplePreferences {
        try {
            return ProtoBuf.decodeFromByteArray(input.readBytes())
        } catch (exception: SerializationException) {
            throw CorruptionException("Cannot read AppStatusPreferences proto", exception)
        }
    }

    override suspend fun writeTo(t: ExamplePreferences, output: OutputStream) = output.write(ProtoBuf.encodeToByteArray(t))

}