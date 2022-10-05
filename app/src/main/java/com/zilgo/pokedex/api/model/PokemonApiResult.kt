package com.zilgo.pokedex.api.model

import android.os.Parcel
import android.os.Parcelable
import com.zilgo.pokedex.domain.PokemonType
import kotlinx.android.parcel.Parcelize

data class PokemonsListApiResult(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResult>
)

data class PokemonResult(
    val name: String,
    val url: String
)

data class PokemonApiResult(
    val id: Int,
    val name: String,
    val types: List<PokemonTypeSlot>,
    val abilities: List<PokemonAbilities>,
    val weight: Float,
    val stats: List<PokemonStat>,
    val moves: List<PokemonMoves>
)

data class PokemonTypeSlot(
    val slot: Int,
    val type: PokemonType
)

@Parcelize
data class PokemonAbilities(
    val ability: PokemonAbility,
    val is_hidden: Boolean,
    val slot: Int
) : Parcelable

@Parcelize
data class PokemonAbility(
    val name: String,
    val url: String
) : Parcelable

data class Ability(
    val id: String,
    val name: String,
)

data class PokemonStat(
    val base_stat: Int,
    val effort: Int,
    val stat: PokemonStatDescription
)

data class PokemonStatDescription(
    val name: String,
    val url: String
)

@Parcelize
data class PokemonMoves(
    val move: PokemonMove
) : Parcelable

@Parcelize
data class PokemonMove(
    val name: String,
    val url: String
) : Parcelable