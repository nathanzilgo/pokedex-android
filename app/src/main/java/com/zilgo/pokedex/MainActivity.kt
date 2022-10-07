package com.zilgo.pokedex


import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zilgo.pokedex.domain.Pokemon
import com.zilgo.pokedex.view.PokemonAdapter
import com.zilgo.pokedex.view.PokemonDialogFragment
import com.zilgo.pokedex.viewmodel.LoadingSpinner
import com.zilgo.pokedex.viewmodel.PokemonViewModel
import com.zilgo.pokedex.viewmodel.PokemonViewModelFactory
import kotlinx.coroutines.flow.Flow

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, View.OnClickListener {

    private var mediaPlayer: MediaPlayer? = null

    private val recyclerView by lazy {
        findViewById<RecyclerView>(R.id.rvPokemons)
    }

    private val progressBar by lazy {
        findViewById<ConstraintLayout>(R.id.progressBar)
    }

    val sharedPref = getSharedPreferences("pokemons", AppCompatActivity.MODE_PRIVATE)

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
//               TODO: save sharedpreferences

            }
        }

        appContext = this.baseContext
    }
    companion object {
        lateinit  var appContext: Context
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val search = menu?.findItem(R.id.ic_menu_search)
        val searchView = search?.actionView as? SearchView
        val musicBtn = menu?.findItem(R.id.ic_menu_art) as? Button

        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        musicBtn?.setOnClickListener(this)
        Toast.makeText(this, "Click on Charmander to play music!\nClick again to stop",
            Toast.LENGTH_LONG).show()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.ic_menu_art -> {
                playOST()
                true
            }
            else -> false
        }
    }

    private fun loadRecyclerView(pokemons: List<Pokemon?>) {
        val adapter = PokemonAdapter(pokemons as List<Pokemon>)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
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

    fun playOST() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.pokemon_ost)
            mediaPlayer?.setLooping(true)
            mediaPlayer?.start()
            Toast.makeText(this, "Now playing: \nPokemon Sapphire Litteroot Town!"
                , Toast.LENGTH_LONG).show()
        } else {
            stopOST()
        }
    }

    private fun stopOST() {
        if (mediaPlayer != null) {
            cleansePlayer()
        }
    }

    private fun cleansePlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
        Toast.makeText(this, "Media player released!", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(p0: View?) {
        playOST()
    }

    private suspend fun saveLocal(pokemons: List<Pokemon?>) {


        val editor = sharedPref.edit()
        val parser = Gson()
        val json = parser.toJson(pokemons)
        editor.putString("pokemons", json)
        editor.apply()
    }

//    companion object {
//        val sharedPreferences = getSharedPreferences
//    }
//    public final class SharePreferencesObject() {
//        val sharedPref = getSharedPreferences("pokemons", AppCompatActivity.MODE_PRIVATE)
//        val editor = sharedPref.edit()
//        val parser = Gson()
//        val json = parser.toJson(pokemons)
//        editor.putString("pokemons", json)
//        editor.apply()
//    }
}

class Prefs (context: Context) {

    private val POKEMONS_LIST = "pokemonsPref"

    private val preferences : SharedPreferences = context.getSharedPreferences(POKEMONS_LIST, Context.MODE_PRIVATE)

    var pokemons = mutableListOf<Pokemon>()
    val parser = Gson()

//    val tinyDb = TinyDB(context)
    var stringJson: String
        get() = preferences.getString(POKEMONS_LIST, null).toString()
        set(value) = preferences.edit().putString(POKEMONS_LIST, stringJson).apply()

//    fun getPokemons() = parser.fromJson<Pokemon>(stringJson)
}


