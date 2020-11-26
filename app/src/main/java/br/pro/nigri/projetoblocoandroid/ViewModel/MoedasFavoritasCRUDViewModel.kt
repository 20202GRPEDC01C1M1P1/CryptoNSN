package br.pro.nigri.projetoblocoandroid.ViewModel

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.pro.nigri.projetoblocoandroid.CotacoesActivity
import br.pro.nigri.projetoblocoandroid.Fragments.MoedaDetailsFragment
import br.pro.nigri.projetoblocoandroid.Model.MoedaModel
import br.pro.nigri.projetoblocoandroid.api.RetroFitClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_moeda_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoedasFavoritasCRUDViewModel: ViewModel() {

    var firebaseFirestore = FirebaseFirestore.getInstance()
    var collection = firebaseFirestore.collection("MoedasFavoritas")
    var idUser = FirebaseAuth.getInstance().currentUser!!.uid
    var moedaEscolhida:String? = null
    var detailsMoeda: MoedaModel?=null

    fun createMoedaFavorita(cryptoMoeda:String): Task<Void> {

        var idDoc = "$idUser-$cryptoMoeda"
        var document = collection.document(idDoc)

        var taskFireStore = document.set(
            mapOf(
                "cryptomoeda" to cryptoMoeda
            )
        )

        return taskFireStore
    }

    fun getCryptoDetails(cryptoMoeda: String, context: Context){

        var nameCrypto = "${cryptoMoeda}-brl"
        var call = RetroFitClient.getCryptoService().getCryptoCurrency(nameCrypto)
        call.enqueue(
            object : Callback<MoedasListViewModel> {


                override fun onResponse(
                    call: Call<MoedasListViewModel>,
                    response: Response<MoedasListViewModel>
                ) {
                    var cryptoMoedas = response.body()
                    detailsMoeda = cryptoMoedas!!.ticker!!
                }

                override fun onFailure(call: Call<MoedasListViewModel>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()

                }
            }
        )
    }
}