package com.zilgo.pokedex.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zilgo.pokedex.R
import com.zilgo.pokedex.domain.Pokemon

class PokemonDialogFragment(val pokemon: Pokemon) :
    Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_pokemon_details, container, false)
    }
}