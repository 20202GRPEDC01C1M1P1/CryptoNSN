package br.pro.nigri.projetoblocoandroid.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.pro.nigri.projetoblocoandroid.Adapter.CotacaoAdapter
import br.pro.nigri.projetoblocoandroid.Model.MoedaModel
import br.pro.nigri.projetoblocoandroid.R
import br.pro.nigri.projetoblocoandroid.ViewModel.MoedaViewModel
import br.pro.nigri.projetoblocoandroid.ViewModel.MoedasFavoritasCRUDViewModel
import br.pro.nigri.projetoblocoandroid.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_favoritos.*
import kotlinx.android.synthetic.main.fragment_lista_moedas.*


class FavoritosFragment : Fragment() {

    private lateinit var moedasFavoritasCRUDViewModel: MoedasFavoritasCRUDViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarRecyclerView()

        viewModelFactory = ViewModelFactory()
        activity?.let {
            moedasFavoritasCRUDViewModel =
                ViewModelProvider(it, viewModelFactory) // MainActivity
                    .get(MoedasFavoritasCRUDViewModel::class.java)
        }

        moedasFavoritasCRUDViewModel.getFavListByUser(requireContext())

        moedasFavoritasCRUDViewModel.listaFavoritos.observe(viewLifecycleOwner, Observer {lista-> if (lista != null){
        }
            if (lista != null){
                // recupera o adapter da RecyclerView
                val adapter = lista_favoritos.adapter

                if (adapter is CotacaoAdapter){
                    adapter.atualizarDados(lista)
                }
            }

        })

    }

    private fun configurarRecyclerView() {
        lista_favoritos.layoutManager =
            LinearLayoutManager(activity)
        lista_favoritos.adapter = CotacaoAdapter(requireContext(),findNavController())
    }

}