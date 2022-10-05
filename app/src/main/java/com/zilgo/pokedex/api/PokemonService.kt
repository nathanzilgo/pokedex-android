package com.zilgo.pokedex.api

import com.zilgo.pokedex.api.model.PokemonApiResult
import com.zilgo.pokedex.api.model.PokemonsListApiResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon")
    fun listPokemons(@Query("limit") limit: Int): Call<PokemonsListApiResult>

    @GET("pokemon/{number}")
    fun getPokemon(@Path("number") number: Int): Call<PokemonApiResult>
}