package br.pro.nigri.projetoblocoandroid.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.pro.nigri.projetoblocoandroid.R
import kotlinx.android.synthetic.main.fragment_lista_moedas.*

class ListaMoedasFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_moedas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_calc_conversao.setOnClickListener{
            findNavController().navigate(R.id.calculadoraConversaoFragment)
        }



    }

}