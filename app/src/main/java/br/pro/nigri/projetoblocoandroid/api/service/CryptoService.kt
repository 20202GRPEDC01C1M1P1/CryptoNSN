package br.pro.nigri.projetoblocoandroid.api.service

import br.pro.nigri.projetoblocoandroid.Model.MoedaModel
import br.pro.nigri.projetoblocoandroid.ViewModel.MoedasListViewModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoService {
    @GET("api/ticker/{currency}")
    fun getCryptoCurrency(@Path("currency") currency:String): Call<MoedasListViewModel>
}