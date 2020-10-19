package br.pro.nigri.projetoblocoandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.pro.nigri.projetoblocoandroid.ViewModel.ListCotacoesViewModel

class ViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T{

        if (modelClass.isAssignableFrom(ListCotacoesViewModel::class.java)){
            return ListCotacoesViewModel() as T
        }


        throw IllegalArgumentException("Classe ViewModel Desconhecida")
    }
}