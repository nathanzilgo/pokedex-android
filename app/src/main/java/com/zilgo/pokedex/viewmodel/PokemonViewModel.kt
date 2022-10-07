package com.zilgo.pokedex.viewmodel

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
import kotlinx.coroutines.flow.Flow

class PokemonViewModel : ViewModel() {

    var pokemons = MutableLiveData<List<Pokemon>>()
    private var pokemonsList = mutableListOf<Pokemon>()

    private lateinit var dataStore: DataStore<Preferences>

    init {
        Thread(Runnable {
            loadPokemons()
        }).start()
    }

    private fun loadPokemons() {

        if
        val pokemonsListApiResult = PokemonRepository.listPokemons()

        pokemonsListApiResult?.results?.let {
            it.map { pokemonResult ->
                // ID:
                val id = pokemonResult.url
                    .replace("https://pokeapi.co/api/v2/pokemon/", "")
                    .replace("/", "").toInt()

                // Pega o pokemon pelo ID
                val pokemonApiResult = PokemonRepository.getPokemon(id)

                pokemonApiResult?.let { pokemonApiResult ->
                    Pokemon(
                        pokemonApiResult.id,
                        pokemonApiResult.name,
                        pokemonApiResult.types.map { type ->
                            type.type
                        },
                        pokemonApiResult.abilities,
                        pokemonApiResult.weight,
                        pokemonApiResult.moves,
                        pokemonApiResult.stats,
                    )
                }
            }.let { it1 ->
                pokemons.postValue(it1 as List<Pokemon>?)
                pokemonsList = it1 as MutableList<Pokemon>
            }
        }
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

    private suspend fun saveLocal(pokemons: List<Pokemon?>) {

        val sharedPref = getSharedPreferences("pokemons", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val parser = Gson()
        val json = parser.toJson(pokemons)
        editor.putString("pokemons", json)
        editor.apply()
    }

    private fun readLocal(): Flow<Int> {
        val sharedPref = getSharedPreferences("pokemons", AppCompatActivity.MODE_PRIVATE)
        val parser = Gson()
        val json = sharedPref.getString("pokemons", null)
        val type = TypeToken<List<Pokemon>>().type
    }
}