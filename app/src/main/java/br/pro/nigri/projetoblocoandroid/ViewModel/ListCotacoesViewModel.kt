package br.pro.nigri.projetoblocoandroid.ViewModel

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.pro.nigri.projetoblocoandroid.CotacoesActivity
import br.pro.nigri.projetoblocoandroid.Model.MoedaModel
import br.pro.nigri.projetoblocoandroid.api.RetroFitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class ListCotacoesViewModel():ViewModel() {

    private var cotacoes: MutableList<MoedaViewModel>? = ArrayList()
    var listaHome = MutableLiveData<List<MoedaViewModel>>()
    var atualizacao:String?=null
    var listaCryptos = mutableListOf(
        "btc-brl",
        "eth-brl",
        "xrp-brl",
        "bch-brl",
        "link-brl",
        "ltc-brl",
        "ada-brl",
        "dot-brl",
        "bnb-brl"
    )

    fun chamarApi(context: Context) {
        cotacoes!!.clear()

        listaCryptos.forEach {

            var call = RetroFitClient.getCryptoService().getCryptoCurrency(it)
            call.enqueue(
                object : Callback<MoedasListViewModel> {

                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onResponse(
                        call: Call<MoedasListViewModel>,
                        response: Response<MoedasListViewModel>
                    ) {
                        var cryptoMoedas = response.body()

                        var lista: MutableList<MoedaViewModel>
                        lista = appendToList(cryptoMoedas?.ticker!!)
                        listaHome.value = lista

                        atualizacao = FormatDateTime()
                    }

                    override fun onFailure(call: Call<MoedasListViewModel>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                        atualizacao = "Falha"
                    }
                }
            )
        }

    }

    private fun appendToList(item: MoedaModel):MutableList<MoedaViewModel>
    {
        val priceDouble = item.price
        val priceString: String = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(
            priceDouble
        )

        var moedaViewModel = MoedaViewModel(item.base, priceString)

        cotacoes!!.add(moedaViewModel)
        cotacoes!!.sortBy { x -> x.base }

        return cotacoes!!
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun FormatDateTime():String{

        val agora = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"))


        // formatar a data
        val formatterData: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
        val dataFormatada: String = formatterData.format(agora)

        // formatar a hora
        val formatterHora: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val horaFormatada: String = formatterHora.format(agora)

        val dataCerta = "Última Atualização: ${dataFormatada} ${horaFormatada}"

        return dataCerta
    }
}
