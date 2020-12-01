package br.pro.nigri.projetoblocoandroid.api

import br.pro.nigri.projetoblocoandroid.api.service.CryptoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitClient {
    private val retrofit: Retrofit = Retrofit
        .Builder()
        .baseUrl("https://api.cryptonator.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getCryptoService():CryptoService {
        return retrofit.create(CryptoService::class.java)
    }
}