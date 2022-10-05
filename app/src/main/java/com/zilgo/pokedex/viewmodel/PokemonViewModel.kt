package com.zilgo.pokedex.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zilgo.pokedex.api.PokemonRepository
import com.zilgo.pokedex.domain.Pokemon
import com.zilgo.pokedex.domain.PokemonType

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
}