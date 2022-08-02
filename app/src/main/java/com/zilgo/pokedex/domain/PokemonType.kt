package com.zilgo.pokedex.domain

import com.zilgo.pokedex.R

data class PokemonType (
    val name: String
) {
    fun mapColor(): Int {
        return when (name.lowercase()) {
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
}
