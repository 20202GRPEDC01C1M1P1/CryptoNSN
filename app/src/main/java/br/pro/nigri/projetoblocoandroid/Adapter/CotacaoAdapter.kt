@file:Suppress("DEPRECATION")

package br.pro.nigri.projetoblocoandroid.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import br.pro.nigri.projetoblocoandroid.R
import br.pro.nigri.projetoblocoandroid.ViewModel.MoedaViewModel
import br.pro.nigri.projetoblocoandroid.ViewModel.MoedasFavoritasCRUDViewModel
import br.pro.nigri.projetoblocoandroid.ViewModelFactory


class CotacaoAdapter(
    var context: Context,
    var navController: NavController,
    var listaMoedas: List<MoedaViewModel> = listOf()
): RecyclerView.Adapter<CotacaoAdapter.MoedaViewHolder>() {


    class MoedaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val nomeMoeda: TextView = itemView.findViewById(R.id.label_nome_moeda)
        val cotacaoMoeda: TextView = itemView.findViewById(R.id.label_cotacao_moeda)
        var cardMoeda:ConstraintLayout = itemView.findViewById(R.id.cardMoeda)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoedaViewHolder {
        val card = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.moedas_card, parent, false)

        return MoedaViewHolder(card)
    }

    override fun getItemCount() = listaMoedas.size


    override fun onBindViewHolder(holder: MoedaViewHolder, position: Int) {

        holder.nomeMoeda.text = listaMoedas[position].base
        holder.cotacaoMoeda.text = listaMoedas[position].price.toString()

        holder.cardMoeda.setOnClickListener{

            var moedasFavoritasCRUDViewModel =
                ViewModelProviders.of(context as FragmentActivity).get(MoedasFavoritasCRUDViewModel::class.java)

            moedasFavoritasCRUDViewModel.getCryptoDetails(holder.nomeMoeda.text.toString(),context)

            navController.navigate(R.id.moedaDetailsFragment)
        }

    }


    fun atualizarDados(moedas: List<MoedaViewModel>)
    {
        listaMoedas = moedas

        notifyDataSetChanged()
    }
}