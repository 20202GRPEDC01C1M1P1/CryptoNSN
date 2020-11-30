@file:Suppress("DEPRECATION")

package br.pro.nigri.projetoblocoandroid.ViewModel

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import br.pro.nigri.projetoblocoandroid.CotacoesActivity
import br.pro.nigri.projetoblocoandroid.Fragments.MoedaDetailsFragment
import br.pro.nigri.projetoblocoandroid.Model.MoedaModel
import br.pro.nigri.projetoblocoandroid.ViewModelFactory
import br.pro.nigri.projetoblocoandroid.api.RetroFitClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_moeda_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoedasFavoritasCRUDViewModel: ViewModel() {

    var firebaseFirestore = FirebaseFirestore.getInstance()
    var collection = firebaseFirestore.collection("MoedasFavoritas")
    var idUser = FirebaseAuth.getInstance().currentUser!!.uid
    var detailsMoeda: MoedaModel?=null
    var listaFavoritos = MutableLiveData<List<MoedaViewModel>>()


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

    fun addFav(cryptoName:String):Task<Void>{
        var document = collection.document("${idUser}-${cryptoName}")

        var taskFireStore = document.set(
            mapOf(
                "base" to cryptoName,
                "user" to idUser
            )
        )

        return taskFireStore
    }

    fun getFavListByUser(context: Context){

        var task = collection.whereEqualTo("user", idUser).get()
        task.addOnSuccessListener {
            val listaFavoritosFirebase = it.toObjects(MoedaViewModel::class.java)

            var listCotacoesViewModel =
                ViewModelProviders.of(context as FragmentActivity).get(ListCotacoesViewModel::class.java)

            var listaDeFavoritos  = listCotacoesViewModel.chamarApiListaFavoritos(listaFavoritosFirebase,context)
            listaFavoritos.value = listaDeFavoritos.value
        }
        task.addOnFailureListener {
            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        }
    }
}