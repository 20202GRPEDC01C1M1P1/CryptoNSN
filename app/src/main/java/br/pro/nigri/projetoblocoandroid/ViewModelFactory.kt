package br.pro.nigri.projetoblocoandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.pro.nigri.projetoblocoandroid.ViewModel.ListCotacoesViewModel
import br.pro.nigri.projetoblocoandroid.ViewModel.LoginViewModel
import br.pro.nigri.projetoblocoandroid.ViewModel.MoedasFavoritasCRUDViewModel
import br.pro.nigri.projetoblocoandroid.ViewModel.UsuarioCRUDViewModel

class ViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T{

        if (modelClass.isAssignableFrom(ListCotacoesViewModel::class.java)){
            return ListCotacoesViewModel() as T
        }
        if (modelClass.isAssignableFrom(UsuarioCRUDViewModel::class.java)){
            return UsuarioCRUDViewModel() as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel() as T
        }
        if (modelClass.isAssignableFrom(MoedasFavoritasCRUDViewModel::class.java)){
            return MoedasFavoritasCRUDViewModel() as T
        }

        throw IllegalArgumentException("Classe ViewModel Desconhecida")
    }
}