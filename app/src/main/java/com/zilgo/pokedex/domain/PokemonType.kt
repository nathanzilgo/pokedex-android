package com.zilgo.pokedex.domain

import android.os.Parcelable
import com.zilgo.pokedex.R
import kotlinx.android.parcel.Parcelize

@kotlinx.serialization.Serializable
@Parcelize
data class PokemonType (
    val name: String? = "",
    val generation: List<PokemonGeneration>? = mutableListOf()
) : Parcelable {
    fun mapColor(): Int {
        return when (name?.lowercase()) {
            "fire" -> R.color.fire
            "water" -> R.color.water
            "poison" -> R.color.poison
            "electric" -> R.color.electric
            "ground" -> R.color.ground
            "bug" -> R.color.bug
            "ghost" -> R.color.ghost
            "steel" -> R.color.steel
            "dark" -> R.color.dark
            "dragon" -> R.color.dragon
            "ice" -> R.color.ice
            "psychic" -> R.color.psychic
            "grass" -> R.color.grass
            "flying" -> R.color.flying
            else -> R.color.white
        }
    }
    fun mapTextColor(): Int {
        return when (name?.lowercase()) {
            "fire", "water", "dragon", "psychic",
            "ghost", "poison" -> R.color.white
            else -> R.color.black
        }
    }
}

@kotlinx.serialization.Serializable
@Parcelize
data class PokemonGeneration(
    val name: String? = "",
    val url: String? = ""
) : Parcelable
