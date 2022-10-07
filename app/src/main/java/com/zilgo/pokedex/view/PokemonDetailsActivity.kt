package com.zilgo.pokedex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zilgo.pokedex.R
import com.zilgo.pokedex.api.model.PokemonMoves
import kotlinx.android.synthetic.main.activity_pokemon_details.*

class PokemonDetailsActivity() : AppCompatActivity() {

    private lateinit var obj: Bundle

    private val recyclerView by lazy {
        findViewById<RecyclerView>(R.id.rvMoves)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)
        window.decorView.apply { systemUiVisibility=
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN }

        obj = intent.getParcelableExtra("pokemon")!!
        val moves = obj.get("moves")
        loadRecyclerView(moves as List<PokemonMoves?>)
        setData(obj)
    }

    private fun setData(obj: Bundle) {
        pokemon_name.text = obj.get("name").toString()
        pokemon_weight.text = obj.get("weight").toString()
        val img = obj.get("image").toString()
        Glide.with(applicationContext).load(img).into(pokemon_photo)
    }

    private fun loadRecyclerView(moves: List<PokemonMoves?>) {
        val adapter = PokemonDetailsAdapter(moves as List<PokemonMoves>)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
}