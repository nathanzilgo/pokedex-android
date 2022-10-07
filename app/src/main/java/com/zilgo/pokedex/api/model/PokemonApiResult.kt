package com.zilgo.pokedex.api.model

import android.os.Parcelable
import com.zilgo.pokedex.api.PokemonRepository
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
    val moves: List<PokemonMoves>,
    val height: Int,
    val base_experience: Int
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
    val url: String,
    var ability: Ability
) : Parcelable {
    init {
        getAbility()
    }
    private fun getAbility() {
        ability = PokemonRepository.getAbility(url)!!
    }
}

@Parcelize
data class Ability(
    val id: String,
    val name: String,
    val effect_entries: List<EffectEntry>
) : Parcelable

@Parcelize
data class EffectEntry(
    val effect: String,
    val short_effect: String
) : Parcelable

@Parcelize
data class PokemonStat(
    val base_stat: Int,
    val effort: Int,
    val stat: PokemonStatDescription
) : Parcelable

@Parcelize
data class PokemonStatDescription(
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class PokemonMoves(
    val move: PokemonMoveObject
) : Parcelable

@Parcelize
data class PokemonMoveObject(
    val name: String,
    val url: String,
    var move: Move
) : Parcelable {
    init {
        getMove()
    }
    private fun getMove() {
        move = PokemonRepository.getMove(url)!!
    }
}

@Parcelize
data class Move(
    val id: Int,
    val accuracy: Int,
    val power: Int,
    val pp: Int,
    val name: String,
) : Parcelable
