package com.lucasdonato.pokemon_api.mechanism.dependencies

import com.lucasdonato.pokemon_api.data.remote.WebServiceClient
import com.lucasdonato.pokemon_api.data.remote.dataSource.PokemonDataSource
import com.lucasdonato.pokemon_api.data.repository.pokemon.PokemonRepository
import com.lucasdonato.pokemon_api.data.useCase.PokemonUseCase
import com.lucasdonato.pokemon_api.presentation.details.presenter.DetailsPresenter
import com.lucasdonato.pokemon_api.presentation.home.presenter.HomePresenter
import org.koin.dsl.module

val presenterModules = module {
    factory { HomePresenter(get()) }
    factory { DetailsPresenter(get()) }
}

val useCaseModules = module {
    factory { PokemonUseCase(get()) }
}

val repositoryModules = module {
    factory { PokemonRepository(get()) }
}

val dataSourceModules = module {
    factory { PokemonDataSource(get()) }
}

val webServiceModules = module {
    single { WebServiceClient().webService }
}

val applicationModules =
    listOf(
        presenterModules, useCaseModules, repositoryModules, dataSourceModules,
        webServiceModules
    )