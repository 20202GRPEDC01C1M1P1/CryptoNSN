package br.pro.nigri.projetoblocoandroid.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.pro.nigri.projetoblocoandroid.CotacoesActivity
import br.pro.nigri.projetoblocoandroid.MainActivity
import br.pro.nigri.projetoblocoandroid.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_conta.*


class ContaFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_conta, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btnDeslogarConta.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(
                Intent(activity, MainActivity::class.java)
            )
        }
    }

}