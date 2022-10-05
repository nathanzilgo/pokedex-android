package com.zilgo.pokedex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.zilgo.pokedex.R
import com.zilgo.pokedex.domain.Pokemon
import kotlinx.android.synthetic.main.activity_pokemon_details.*

class PokemonDetailsActivity() : AppCompatActivity() {

    private lateinit var obj: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)
        window.decorView.apply { systemUiVisibility=
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN }

        obj = intent.getParcelableExtra("pokemon")!!
        setData(obj)
    }

    private fun setData(obj: Bundle) {
        pokemon_name.text = obj.get("name").toString()
        pokemon_weight.text = obj.get("weight").toString()
        val img = obj.get("image").toString()
        Glide.with(applicationContext).load(img).into(pokemon_photo)
    }
}