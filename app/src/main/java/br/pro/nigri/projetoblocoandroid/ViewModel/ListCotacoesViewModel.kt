package br.pro.nigri.projetoblocoandroid.ViewModel

import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.pro.nigri.projetoblocoandroid.Model.MoedaModel
import br.pro.nigri.projetoblocoandroid.R
import br.pro.nigri.projetoblocoandroid.api.RetroFitClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class ListCotacoesViewModel():ViewModel() {

    private var cotacoes: MutableList<MoedaViewModel>? = ArrayList()
    var msg = MutableLiveData<String>()
    var listaHome = MutableLiveData<List<MoedaViewModel>>()

    var listaCryptos = mutableListOf("btc-brl", "eth-brl","xrp-brl","bch-brl","link-brl","ltc-brl","ada-brl","dot-brl","bnb-brl")

    fun chamarApi() {

        cotacoes!!.clear()
        listaCryptos.forEach {

            var call = RetroFitClient.getCryptoService().getCryptoCurrency(it)
            call.enqueue(
                object : Callback<MoedasListViewModel> {

                    override fun onResponse(
                        call: Call<MoedasListViewModel>,
                        response: Response<MoedasListViewModel>
                    ) {
                        var cryptoMoedas = response.body()

                        var lista = appendToList(cryptoMoedas?.ticker!!)
                        listaHome.value = lista
                    }

                    override fun onFailure(call: Call<MoedasListViewModel>, t: Throwable) {
                        msg.value = t.message
                    }
                }
            )
        }
    }

    private fun appendToList(item: MoedaModel):MutableList<MoedaViewModel>
    {
        val priceDouble = item.price
        val priceString: String = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(priceDouble)

        var moedaViewModel = MoedaViewModel(item.base,priceString)

        cotacoes!!.add(moedaViewModel)
        cotacoes!!.sortBy { x -> x.base }

        return cotacoes!!
    }
}
