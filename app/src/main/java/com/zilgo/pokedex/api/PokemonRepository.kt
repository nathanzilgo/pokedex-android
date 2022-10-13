package com.zilgo.pokedex.api

import com.zilgo.pokedex.api.model.Ability
import com.zilgo.pokedex.api.model.Move
import com.zilgo.pokedex.api.model.PokemonApiResult
import com.zilgo.pokedex.api.model.PokemonsListApiResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PokemonRepository {
    private val service: PokemonService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(PokemonService::class.java)
    }

    fun listPokemons(limit: Int = 151): PokemonsListApiResult? {
        val call = service.listPokemons(limit)
        return call.execute().body()
    }

    fun getPokemon(number: Int): PokemonApiResult? {
        val call = service.getPokemon(number)
        return call.execute().body()
    }

    fun getMove(url: String): MoveApiResult? {
        val call = service.getMove(url)
        return call.execute().body()
    }

    fun getAbility(url: String): AbilityApiResult? {
        val call = service.getAbility(url)
        return call.execute().body()
    }
}