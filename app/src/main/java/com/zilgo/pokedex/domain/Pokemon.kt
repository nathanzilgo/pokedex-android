package com.zilgo.pokedex.domain

data class Pokemon (
    val imageUlr: String,
    val number: Int,
    val name: String,
    val types: List<PokemonType>
    )

data class PokemonType(
    val name: String
)