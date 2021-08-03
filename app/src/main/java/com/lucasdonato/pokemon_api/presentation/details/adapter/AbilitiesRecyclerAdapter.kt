package com.lucasdonato.pokemon_api.presentation.details.adapter

import Abilities
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucasdonato.pokemon_api.R
import com.lucasdonato.pokemon_api.presentation.base.adapter.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.abilities_recycler.view.*

class AbilitiesRecyclerAdapter :
    BaseRecyclerAdapter<Abilities, AbilitiesRecyclerAdapter.ViewHolder>() {

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(mData[position], position)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(
            (R.layout.abilities_recycler), viewGroup,
            false
        )
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(ability: Abilities, position: Int) {
            itemView.apply {
                abilities_recycler_text.text =  ability.ability.name.capitalize()
            }
        }
    }

}