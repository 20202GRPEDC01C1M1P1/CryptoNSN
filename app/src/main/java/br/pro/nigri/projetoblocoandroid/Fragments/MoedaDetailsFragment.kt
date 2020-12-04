package br.pro.nigri.projetoblocoandroid.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.pro.nigri.projetoblocoandroid.Model.MoedaModel
import br.pro.nigri.projetoblocoandroid.R
import br.pro.nigri.projetoblocoandroid.ViewModel.ListCotacoesViewModel
import br.pro.nigri.projetoblocoandroid.ViewModel.MoedasFavoritasCRUDViewModel
import br.pro.nigri.projetoblocoandroid.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_moeda_details.*

class MoedaDetailsFragment : Fragment() {

    private lateinit var moedasFavoritasCRUDViewModel: MoedasFavoritasCRUDViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private var statusFavoritos:Boolean?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_moeda_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFactory = ViewModelFactory()
        activity?.let {
            moedasFavoritasCRUDViewModel =
                ViewModelProvider(it, viewModelFactory)
                    .get(MoedasFavoritasCRUDViewModel::class.java)
        }

        moedasFavoritasCRUDViewModel.detailsMoeda.observe(viewLifecycleOwner, Observer {

            verificarFavoritos(it.base!!)
            txtNomeMoedaDetails.text = "Moeda: ${it.base}"
            txtAlteracaoMoedaDetails.text = "Alteração: ${it.change.toString()}"
            txtValorMoedaDetails.text = "Valor (BRL): ${it.price.toString()}"


            btnAddMoedaFav.setOnClickListener { view ->
                if(statusFavoritos!!){
                    actionRemoveFavorito(it.base!!)
                }
                else
                {
                    actionAddFavorito(it.base!!)
                }

            }

            btnAtualizarDetails.setOnClickListener { view ->
                updateInfo(it.base!!)
            }
        })
    }

    private fun verificarFavoritos(moeda:String){
        moedasFavoritasCRUDViewModel.checkFav(moeda){
            if (!it.isEmpty) {
                statusFavoritos = true
                btnAddMoedaFav.text = "Remover dos favoritos"
                btnAddMoedaFav.isClickable = true

            }else{
                statusFavoritos = false
                btnAddMoedaFav.text = "Adicionar moeda aos favoritos"
                btnAddMoedaFav.isClickable = true

            }
        }
    }

    private fun updateInfo(moeda:String){
        moedasFavoritasCRUDViewModel.getCryptoDetails(moeda,"brl",requireContext())
    }

    private fun actionAddFavorito(moeda:String){
        var result = moedasFavoritasCRUDViewModel.addFav(moeda)
        result.addOnSuccessListener { void ->
            Toast.makeText(
                requireContext(),
                "Moeda Adicionada como Favorita com Sucesso!",
                Toast.LENGTH_LONG
            ).show()
            verificarFavoritos(moeda)
        }

        result.addOnFailureListener {
            Toast.makeText(
                requireContext(),
                it.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun actionRemoveFavorito(moeda:String){
        var result = moedasFavoritasCRUDViewModel.removerFav(moeda)
        result.addOnSuccessListener { void ->
            Toast.makeText(
                requireContext(),
                "Moeda removida dos favoritos com sucesso!",
                Toast.LENGTH_LONG
            ).show()
            verificarFavoritos(moeda)
        }

        result.addOnFailureListener {
            Toast.makeText(
                requireContext(),
                it.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}