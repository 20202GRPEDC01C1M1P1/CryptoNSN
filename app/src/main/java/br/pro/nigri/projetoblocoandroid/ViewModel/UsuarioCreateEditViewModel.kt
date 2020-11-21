package br.pro.nigri.projetoblocoandroid.ViewModel

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UsuarioCreateEditViewModel:ViewModel() {

    fun createAuthUsuario(email: String, senha:String):Task<AuthResult> {

        var firebaseAuth = FirebaseAuth.getInstance()
        var task = firebaseAuth
            .createUserWithEmailAndPassword(email, senha)

        return task
    }

    fun createInfoUsuario(nomeUsuario:String,id: String):Task<Void>{

        var firebaseFirestore = FirebaseFirestore.getInstance()
        var collection = firebaseFirestore.collection("usuario")
        var document = collection.document(id)

        var taskFireStore = document.set(
            mapOf(
                "nome" to nomeUsuario
            )
        )

        return taskFireStore
    }
}