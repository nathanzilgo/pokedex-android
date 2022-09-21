package com.zilgo.pokedex


import android.os.Bundle
import android.view.Menu
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zilgo.pokedex.domain.Pokemon
import com.zilgo.pokedex.view.PokemonAdapter
import com.zilgo.pokedex.viewmodel.LoadingSpinner
import com.zilgo.pokedex.viewmodel.PokemonViewModel
import com.zilgo.pokedex.viewmodel.PokemonViewModelFactory


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val recyclerView by lazy {
        findViewById<RecyclerView>(R.id.rvPokemons)
    }

    private val progressBar by lazy {
        findViewById<ConstraintLayout>(R.id.progressBar)
    }

    val loadingSpinner = LoadingSpinner(this)

    private val viewModel by lazy {
        ViewModelProvider(this, PokemonViewModelFactory())
            .get(PokemonViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create the observer which updated the UI
        viewModel.pokemons.observe(this) {
            // Update the UI, in this case, a RecyclerView
            loadingSpinner.startLoading()
            loadRecyclerView(it)
            if (viewModel.pokemons.value != null) {
                progressBar.visibility = View.GONE
                loadingSpinner.dismiss()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val search = menu?.findItem(R.id.ic_menu_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        return true
    }

    private fun loadRecyclerView(pokemons: List<Pokemon?>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PokemonAdapter(pokemons as List<Pokemon>)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchPokemons(query)
            return true
        }
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchPokemons(query)
            return true
        }
        return false
    }

    private fun searchPokemons(query: String) {
        viewModel.searchPokemons(query.lowercase())
    }
}
