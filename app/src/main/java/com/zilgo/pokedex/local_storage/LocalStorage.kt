package com.zilgo.pokedex.local_storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.zilgo.pokedex.domain.Pokemon
import kotlinx.coroutines.flow.Flow

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "pokemon_data_store")
interface LocalStorage {
    fun isPokemonsSaved(): Flow<Boolean>
    fun getLocalPokemons(): Flow<List<Pokemon>>

    suspend fun savePokemons()
}