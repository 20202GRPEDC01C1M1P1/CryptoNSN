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
import br.pro.nigri.projetoblocoandroid.R
import br.pro.nigri.projetoblocoandroid.ViewModel.ListCotacoesViewModel
import br.pro.nigri.projetoblocoandroid.ViewModel.MoedasFavoritasCRUDViewModel
import br.pro.nigri.projetoblocoandroid.ViewModel.MoedasListViewModel
import br.pro.nigri.projetoblocoandroid.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_lista_moedas.*

class ListaMoedasFragment : Fragment() {

    private lateinit var listCotacoesViewModel: ListCotacoesViewModel
    private lateinit var moedasFavoritasCRUDViewModel: MoedasFavoritasCRUDViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_moedas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBarListHome.visibility = View.VISIBLE
        configurarRecyclerView()
        popular()

    }

    private fun configurarRecyclerView() {
        lista_moedas.layoutManager =
            LinearLayoutManager(activity)
        lista_moedas.adapter = CotacaoAdapter(){

            viewModelFactory = ViewModelFactory()
            activity?.let {
                moedasFavoritasCRUDViewModel =
                    ViewModelProvider(it, viewModelFactory) // MainActivity
                        .get(MoedasFavoritasCRUDViewModel::class.java)
            }

            moedasFavoritasCRUDViewModel.getCryptoDetails(it,"brl",requireContext())

            findNavController().navigate(R.id.moedaDetailsFragment)
        }
    }

    private fun popular(){

        viewModelFactory = ViewModelFactory()
        activity?.let {
            listCotacoesViewModel =
                ViewModelProvider(it, viewModelFactory) // MainActivity
                    .get(ListCotacoesViewModel::class.java)
        }

        listCotacoesViewModel.chamarApiListaHome(requireContext())

        listCotacoesViewModel.listaHome.observe(viewLifecycleOwner, Observer {lista-> if (lista != null){
        }
            if (lista != null){
                // recupera o adapter da RecyclerView
                val adapter = lista_moedas.adapter

                if (adapter is CotacaoAdapter){
                    adapter.atualizarDados(lista)
                    txtUltimaAtualizacao.text = listCotacoesViewModel.atualizacao
                    progressBarListHome.visibility = View.GONE

                }
            }


        })


    }

}