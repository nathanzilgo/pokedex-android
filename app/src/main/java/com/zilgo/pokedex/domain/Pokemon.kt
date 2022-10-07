package com.zilgo.pokedex.domain

import android.os.Parcelable
import com.zilgo.pokedex.api.model.PokemonAbilities
import com.zilgo.pokedex.api.model.PokemonMoves
import com.zilgo.pokedex.api.model.PokemonStat
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Pokemon(
    val number: Int,
    val name: String,
    val types: List<PokemonType>,
    val ability: List<PokemonAbilities>,
    val weight: Float,
    val moves: List<PokemonMoves>,
    val stats: List<PokemonStat>
) : Parcelable {
    val formattedName = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }

    val formattedNumber = number.toString().padStart(3, '0')

    val formattedWeight = "${weight / 10.0} Kg"

    val imageUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/$formattedNumber.png"
}
