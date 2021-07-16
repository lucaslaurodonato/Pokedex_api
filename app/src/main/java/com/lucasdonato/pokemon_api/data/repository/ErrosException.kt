package com.lucasdonato.pokemon_api.data.repository

import com.lucasdonato.pokemon_api.data.repository.ErrorCode
import java.lang.RuntimeException

class ErrosException(var errorCode: ErrorCode, var errorMessage: String? = "") : RuntimeException()


