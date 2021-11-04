package com.lucasdonato.pokemon_api.presentation.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lucasdonato.pokemon_api.R
import com.lucasdonato.pokemon_api.data.model.Results
import com.lucasdonato.pokemon_api.databinding.ViewPokemonListBinding
import com.lucasdonato.pokemon_api.mechanism.extensions.formattedNumber
import com.lucasdonato.pokemon_api.mechanism.utils.Utils
import com.lucasdonato.pokemon_api.presentation.base.adapter.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.view_pokemon_list.view.*

class PokemonRecyclerAdapter : BaseRecyclerAdapter<Results, PokemonRecyclerAdapter.ViewHolder>() {

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(mData[position], position)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.view_pokemon_list,
                viewGroup,
                false
            )
        )
    }

    inner class ViewHolder(binding: ViewPokemonListBinding) : RecyclerView.ViewHolder(binding.root) {
        private val name = binding.namePokemon
        private val container = binding.container
        private val imagePokemon = binding.imagePokemon
        private val imageProgress = binding.imageProgress

        fun bind(pokemon: Results, position: Int) {
            pokemon.number = position + 1
            pokemon.imageUrl = formattedNumber(pokemon.number)

            name.text = pokemon.name?.capitalize()
            container.setBackgroundResource(R.drawable.shape_home_background)
            Utils.setImageUrl(pokemon.imageUrl, imagePokemon, imageProgress)
            container.setOnClickListener { onItemClickListener?.invoke(pokemon) }
        }
    }

}