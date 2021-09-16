package com.lucasdonato.pokemon_api.presentation.home.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.lucasdonato.pokemon_api.R
import com.lucasdonato.pokemon_api.data.model.Results
import com.lucasdonato.pokemon_api.mechanism.extensions.formattedNumber
import com.lucasdonato.pokemon_api.mechanism.utils.Utils
import com.lucasdonato.pokemon_api.presentation.AppApplication.Companion.context
import com.lucasdonato.pokemon_api.presentation.base.adapter.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.view_pokemon_list.view.*

class PokemonRecyclerAdapter : BaseRecyclerAdapter<Results, PokemonRecyclerAdapter.ViewHolder>() {

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(mData[position], position)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(
            (R.layout.view_pokemon_list), viewGroup,
            false
        )
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(pokemon: Results, position: Int) {

            pokemon.number = position + 1
            pokemon.imageUrl = formattedNumber(pokemon.number)

            itemView.apply {

                name_pokemon.text = pokemon.name?.capitalize()
                container.setBackgroundResource(R.drawable.shape_home_background)
                Utils.setImageUrl(context, pokemon.imageUrl, image_pokemon, image_progress)
                container.setOnClickListener { onItemClickListener?.invoke(pokemon) }
            }
        }

    }

}