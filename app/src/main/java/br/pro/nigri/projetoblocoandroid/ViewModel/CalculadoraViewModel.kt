package br.pro.nigri.projetoblocoandroid.ViewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.pro.nigri.projetoblocoandroid.Model.MoedaModel
import br.pro.nigri.projetoblocoandroid.api.RetroFitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CalculadoraViewModel:ViewModel() {
    private var detailsMoeda = MutableLiveData<MoedaModel>()

    fun calcularConversao(){

    }

    private fun getCryptoDetails(cryptoMoeda: String, moedaConvertida:String,context: Context){

        var nameCrypto = "${cryptoMoeda}-${moedaConvertida}"

        var call = RetroFitClient.getCryptoService().getCryptoCurrency(nameCrypto)
        call.enqueue(
            object : Callback<MoedasListViewModel> {


                override fun onResponse(
                    call: Call<MoedasListViewModel>,
                    response: Response<MoedasListViewModel>
                ) {
                    var cryptoMoedas = response.body()
                    detailsMoeda!!.value = cryptoMoedas!!.ticker!!
                }

                override fun onFailure(call: Call<MoedasListViewModel>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()

                }
            }
        )
    }
}