package br.pro.nigri.projetoblocoandroid.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
                ViewModelProvider(it, viewModelFactory) // MainActivity
                    .get(MoedasFavoritasCRUDViewModel::class.java)
        }

        var model = moedasFavoritasCRUDViewModel.detailsMoeda

        txtNomeMoedaDetails.text = "Moeda: ${model?.base}"
        txtAlteracaoMoedaDetails.text = "Alteração: ${model?.change.toString()}"
        txtValorMoedaDetails.text = "Valor (BRL): ${model?.price.toString()}"

        btnAddMoedaFav.setOnClickListener {
            var result = moedasFavoritasCRUDViewModel.addFav(model!!.base!!)
            result.addOnSuccessListener {
                Toast.makeText(
                    requireContext(),
                    "Moeda Adicionada como Favorita com Sucesso!",
                    Toast.LENGTH_LONG
                ).show()
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
}