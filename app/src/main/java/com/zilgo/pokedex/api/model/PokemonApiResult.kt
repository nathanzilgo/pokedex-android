package com.zilgo.pokedex.api.model

import android.os.Parcelable
import com.zilgo.pokedex.api.PokemonRepository
import com.zilgo.pokedex.domain.PokemonType
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

data class PokemonsListApiResult(
    val count: Int? = Int.MIN_VALUE,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResult>? = mutableListOf()
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
    val base_experience: Int? = Int.MIN_VALUE
)

data class PokemonTypeSlot(
    val slot: Int,
    val type: PokemonType? = PokemonType()
)

@kotlinx.serialization.Serializable
@Parcelize
data class PokemonAbilities(
    val ability: PokemonAbility?,
    val is_hidden: Boolean?,
    val slot: Int?
) : Parcelable

@OptIn(DelicateCoroutinesApi::class)
@kotlinx.serialization.Serializable
@Parcelize
data class PokemonAbility(
    val name: String? = "",
    val url: String? = "",
    var ability: Ability? = Ability()
) : Parcelable {
    init {
        GlobalScope.launch {
            getAbility()
        }
    }
    private fun getAbility() {
        if (url?.isNotEmpty() == true) {
            val apiResult = PokemonRepository.getAbility(url)
            apiResult.let {
                if (it != null) {
                    this.ability = Ability(it.id, it.name, it.effect_entries)
                }
            }
        } else {
            Ability()
        }
    }
}

@kotlinx.serialization.Serializable
@Parcelize
data class Ability(
    val id: Int = Int.MIN_VALUE,
    val name: String? = "",
    val effect_entries: List<EffectEntry>? = mutableListOf()
) : Parcelable

@kotlinx.serialization.Serializable
@Parcelize
data class EffectEntry(
    val effect: String? = "",
    val short_effect: String? = ""
) : Parcelable

@kotlinx.serialization.Serializable
@Parcelize
data class PokemonStat(
    val base_stat: Int? = Int.MIN_VALUE,
    val effort: Int? = Int.MIN_VALUE,
    val stat: PokemonStatDescription? = PokemonStatDescription()
) : Parcelable

@kotlinx.serialization.Serializable
@Parcelize
data class PokemonStatDescription(
    val name: String? = "",
    val url: String? = ""
) : Parcelable

@kotlinx.serialization.Serializable
@Parcelize
data class PokemonMoves(
    val move: PokemonMoveObject? = PokemonMoveObject()
) : Parcelable

@OptIn(DelicateCoroutinesApi::class)
@kotlinx.serialization.Serializable
@Parcelize
data class PokemonMoveObject(
    val name: String? = "",
    val url: String? = "",
    var move: Move? = Move()
) : Parcelable {
    init {
        GlobalScope.launch {
            getMove()
        }
    }
     private fun getMove() {
        val apiResult = url?.let { PokemonRepository.getMove(it) }
         apiResult.let {
             if (it != null) {
                 this.move = Move(it.id, it.accuracy, it.power, it.pp, it.name)
             }
         }
    }
}

@kotlinx.serialization.Serializable
@Parcelize
data class Move(
    val id: Int? = Int.MIN_VALUE,
    val accuracy: Int? = Int.MIN_VALUE,
    val power: Int? = Int.MIN_VALUE,
    val pp: Int? = Int.MIN_VALUE,
    val name: String? = "",
) : Parcelable
