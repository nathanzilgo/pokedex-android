package com.zilgo.pokedex.api

import android.os.Parcelable
import com.zilgo.pokedex.api.model.*
import kotlinx.android.parcel.Parcelize
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Objects

interface PokemonService {
    @GET("pokemon")
    fun listPokemons(@Query("limit") limit: Int): Call<PokemonsListApiResult>

    @GET("pokemon/{number}")
    fun getPokemon(@Path("number") number: Int): Call<PokemonApiResult>

    @GET("{url}")
    fun getMove(@Path("url") url: String): Call<MoveApiResult>

    @GET("{url}")
    fun getAbility(@Path("url") url: String): Call<AbilityApiResult>
}

@kotlinx.serialization.Serializable
@Parcelize
data class MoveApiResult (
    val accuracy: Int,
    val id: Int,
    val power: Int,
    val pp: Int,
    val name: String
    ): Parcelable

@kotlinx.serialization.Serializable
@Parcelize
data class AbilityApiResult (
    val id: Int,
    val name: String,
    val effect_entries: List<EffectEntry>
    ) : Parcelable
