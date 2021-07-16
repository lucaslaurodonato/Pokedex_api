package com.lucasdonato.pokemon_api.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Results(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?,
    var number: Int?,
    var imageUrl: String?
) : Serializable

