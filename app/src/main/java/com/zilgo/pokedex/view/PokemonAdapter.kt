package com.zilgo.pokedex.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zilgo.pokedex.R
import com.zilgo.pokedex.domain.Pokemon


class PokemonAdapter(
    private val items: List<Pokemon?>,
) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    fun getItem(position: Int): Pokemon? {
        return items[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bindView(item)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, PokemonDetailsActivity::class.java)
            val b = Bundle()
            b.putString("name", item?.formattedName)
            b.putString("weight", item?.formattedWeight)
            b.putString("image", item?.imageUrl)
            b.putParcelableArrayList("moves", ArrayList(item?.moves!!))
            b.putParcelableArrayList("stats", ArrayList(item.stats))
//            b.putParcelableArrayList("abilities", ArrayList(item.ability))
            intent.putExtra("pokemon", b)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(item: Pokemon?) = with(itemView) {
            val ivPokemon = findViewById<ImageView>(R.id.ivPokemon)
            val tvNumber = findViewById<TextView>(R.id.tvNumber)
            val tvName = findViewById<TextView>(R.id.tvName)
            val tvType1 = findViewById<TextView>(R.id.tvType1)
            val tvType2 = findViewById<TextView>(R.id.tvType2)

            item?.let {
                Glide.with(itemView.context).load(it.imageUrl).into(ivPokemon)

                tvNumber.text = "Nº ${item.formattedNumber}"
                tvName.text = item.formattedName
                tvType1.text = item.types[0].name?.capitalize() ?: ""
                tvType1.setBackgroundResource(item.types[0].mapColor())

                if (item.types.size > 1) {
                    tvType2.visibility = View.VISIBLE
                    tvType2.text = item.types[1].name?.capitalize() ?: ""
                    tvType2.setBackgroundResource(item.types[1].mapColor())
                } else {
                    tvType2.visibility = View.GONE
                }
            }
        }
    }


}

