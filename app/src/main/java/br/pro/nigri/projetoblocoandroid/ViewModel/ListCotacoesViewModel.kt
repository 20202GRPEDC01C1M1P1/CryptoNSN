package br.pro.nigri.projetoblocoandroid.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.pro.nigri.projetoblocoandroid.Model.MoedaModel

class ListCotacoesViewModel:ViewModel() {
    val cotacoes : MutableLiveData<List<MoedaModel>> by lazy {
        MutableLiveData<List<MoedaModel>>()
    }
}