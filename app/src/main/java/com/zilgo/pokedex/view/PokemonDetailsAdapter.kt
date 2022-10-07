package com.zilgo.pokedex.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.zilgo.pokedex.R
import com.zilgo.pokedex.api.model.PokemonMoves

class PokemonDetailsAdapter (
    private val items: List<PokemonMoves?>
) : RecyclerView.Adapter<PokemonDetailsAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.move_button, parent, false)

        return PokemonDetailsAdapter.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bindView(item)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(item: PokemonMoves?) = with(itemView) {
            val btnMove = findViewById<Button>(R.id.btnMove)
            val move = item?.move
            item.let {
                btnMove.text = move?.name
            }
        }
    }
}