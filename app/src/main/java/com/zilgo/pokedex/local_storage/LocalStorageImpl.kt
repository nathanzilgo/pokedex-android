package com.zilgo.pokedex.local_storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.zilgo.pokedex.domain.Pokemon
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject



class LocalStorageImpl @Inject constructor(
    @ApplicationContext context: Context
) : LocalStorage{

    val POKEMONS_LIST_KEY = stringPreferencesKey("local_pokemons")
    val pokemonsListFlow: Flow<String> = context.dataStore.data
        .map { value: Preferences ->
            value[POKEMONS_LIST_KEY] ?: ""
        }

    override fun isPokemonsSaved(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun getLocalPokemons(): Flow<List<Pokemon>> {
        TODO("Not yet implemented")
    }

    override suspend fun savePokemons() {
        TODO("Not yet implemented")
    }
}