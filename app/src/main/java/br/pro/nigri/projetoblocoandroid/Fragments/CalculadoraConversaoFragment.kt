package br.pro.nigri.projetoblocoandroid.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.pro.nigri.projetoblocoandroid.R
import br.pro.nigri.projetoblocoandroid.ViewModel.MoedasFavoritasCRUDViewModel
import br.pro.nigri.projetoblocoandroid.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_calculadora_conversao.*
import kotlinx.android.synthetic.main.fragment_moeda_details.*


class CalculadoraConversaoFragment : Fragment() {

    private lateinit var moedasFavoritasCRUDViewModel: MoedasFavoritasCRUDViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculadora_conversao, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        popularSpinner()

        btnCalcular.setOnClickListener {
            calcular()
        }
    }

    private fun popularSpinner(){
        var listaCryptos = mutableListOf(
            "BTC",
            "ETH",
            "XRP",
            "BCH",
            "LINK",
            "LTC",
            "ADA",
            "DOT",
            "BNB",
            "BRL",
            "USD"
        )

        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(), android.R.layout.simple_spinner_item,
            listaCryptos
        )
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerConversao1.adapter = spinnerArrayAdapter
        spinnerConversao2.adapter = spinnerArrayAdapter
    }

    private fun calcular(){

        var tipo1 = spinnerConversao1.selectedItem.toString()
        var tipo2 = spinnerConversao2.selectedItem.toString()

        if (tipo1.equals(tipo2))
        {
            Toast.makeText(requireContext(),"As duas moedas não podem ser iguais", Toast.LENGTH_LONG).show()
        }
        else if(txtValorCalcular.text.isEmpty())
        {
            Toast.makeText(requireContext(),"O valor não pode ser nulo", Toast.LENGTH_LONG).show()
        }
        else
        {
            var valor = txtValorCalcular.text.toString().toDouble()

            viewModelFactory = ViewModelFactory()
            activity?.let {
                moedasFavoritasCRUDViewModel =
                    ViewModelProvider(it, viewModelFactory) // MainActivity
                        .get(MoedasFavoritasCRUDViewModel::class.java)
            }

            moedasFavoritasCRUDViewModel.getCryptoDetails(tipo1,tipo2,requireContext())

            moedasFavoritasCRUDViewModel.detailsMoeda.observe(viewLifecycleOwner, Observer {

                var valorMoeda = it.price!!.toDouble()

                var valorFinal = valorMoeda * valor

                txtValorFinal.setText("${valor} ${tipo1} = ${String.format("%.2f", valorFinal)} ${tipo2}")

            })
        }


    }

}