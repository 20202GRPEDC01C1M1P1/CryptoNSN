package br.pro.nigri.projetoblocoandroid.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.pro.nigri.projetoblocoandroid.Model.MoedaModel
import br.pro.nigri.projetoblocoandroid.R
import br.pro.nigri.projetoblocoandroid.ViewModel.MoedaViewModel
import br.pro.nigri.projetoblocoandroid.ViewModelFactory

class CotacaoAdapter(var listaMoedas:List<MoedaViewModel> = listOf()): RecyclerView.Adapter<CotacaoAdapter.MoedaViewHolder>() {

    class MoedaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val nomeMoeda: TextView = itemView.findViewById(R.id.label_nome_moeda)
        val cotacaoMoeda: TextView = itemView.findViewById(R.id.label_cotacao_moeda)

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

    }


    fun atualizarDados(moedas: List<MoedaViewModel>)
    {
        listaMoedas = moedas

        notifyDataSetChanged()
    }
}