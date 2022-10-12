package com.zilgo.pokedex.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zilgo.pokedex.api.PokemonRepository
import com.zilgo.pokedex.domain.Pokemon
import com.zilgo.pokedex.domain.PokemonType
import com.zilgo.pokedex.prefs
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {

    var pokemons = MutableLiveData<List<Pokemon>>()
    private var pokemonsList = mutableListOf<Pokemon>()

    init {
        Thread(Runnable {
            loadPokemons()
        }).start()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadPokemons() {

        if (prefs.storedPokemons.isNotEmpty()) {
            pokemons.postValue(prefs.getPokemonList())
            pokemonsList = prefs.storedPokemons as MutableList<Pokemon>
            return
        }
        val pokemonsListApiResult = PokemonRepository.listPokemons()

        pokemonsListApiResult?.results?.let {
            it.map { pokemonResult ->
                // ID:
                val id = pokemonResult.url
                    .replace("https://pokeapi.co/api/v2/pokemon/", "")
                    .replace("/", "").toInt()

                // Pega o pokemon pelo ID
                val pokemonApiResult = PokemonRepository.getPokemon(id)

                pokemonApiResult?.let { apiResult ->
                    apiResult.types.map { type ->
                        type.type
                    }.let { it1 ->
                        Pokemon(
                            apiResult.id,
                            apiResult.name,
                            it1 as List<PokemonType>,
                            apiResult.abilities,
                            apiResult.weight,
                            apiResult.moves,
                            apiResult.stats,
                        )
                    }
                }
            }.let { it1 ->
                pokemons.postValue(it1 as List<Pokemon>?)
                pokemonsList = it1 as MutableList<Pokemon>
                GlobalScope.launch {
                    taskStorePokemons(it1)
                }
            }
        }
    }

    suspend fun taskStorePokemons(pokemons: List<Pokemon>) {
        prefs.storePokemons(pokemons)
    }

    fun searchPokemons(searchQuery: String) {
        pokemons.postValue(pokemonsList.filter {
            it.name.contains(searchQuery)
        })
    }

    fun filterByType(types: List<PokemonType>) {
        val output = mutableListOf<Pokemon>()

        for (pokemon in pokemonsList) {
            for (type in types) {
                if (pokemon.types.contains(type)) {
                    output.add(pokemon)
                }
            }
        }
        pokemons.postValue(output)
    }
}