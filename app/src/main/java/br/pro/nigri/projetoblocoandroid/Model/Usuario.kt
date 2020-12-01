package br.pro.nigri.projetoblocoandroid.Model

import com.google.firebase.firestore.DocumentId

class Usuario (
    var nome:String? = null,
    @DocumentId var id: String? = null
)