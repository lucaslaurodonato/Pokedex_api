package com.lucasdonato.pokemon_api.presentation.home.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.lucasdonato.pokemon_api.R
import com.lucasdonato.pokemon_api.data.model.Pokemon
import com.lucasdonato.pokemon_api.data.model.Results
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
            ///TODO: FAZER UMA EXTENSION PRA ISSO
            val formattedNumber = pokemon.number.toString().padStart(3, '0')
            pokemon.imageUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/$formattedNumber.png"

            itemView.apply {

                container.setOnClickListener {
                    onItemClickListener?.invoke(pokemon)
                }

                pokemon.imageUrl.let { photoUrl ->
                    Glide.with(context).load(photoUrl)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?, model: Any?,
                                target: Target<Drawable>?, isFirstResource: Boolean
                            ): Boolean {
                                itemView.image_progress.visibility = GONE
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?, model: Any?,
                                target: Target<Drawable>?, dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                itemView.image_progress.visibility = GONE
                                return false
                            }
                        }).into(image_pokemon)
                }

            }
        }

    }

    override fun validateDate() = false

}