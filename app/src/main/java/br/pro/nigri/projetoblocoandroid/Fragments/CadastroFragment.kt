package br.pro.nigri.projetoblocoandroid.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.pro.nigri.projetoblocoandroid.R
import br.pro.nigri.projetoblocoandroid.ViewModel.UsuarioCreateEditViewModel
import br.pro.nigri.projetoblocoandroid.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_cadastro.*
import kotlinx.android.synthetic.main.fragment_login.*

class CadastroFragment : Fragment() {

    private lateinit var viewModel: UsuarioCreateEditViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cadastro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFactory = ViewModelFactory()
        activity?.let {
            viewModel =
                ViewModelProvider(it, viewModelFactory) // MainActivity
                    .get(UsuarioCreateEditViewModel::class.java)
        }
        btnCadastrarUsuario.setOnClickListener {
            var nome = txtNomeCadastrar.text.toString()
            var email = txtEmailCadastrar.text.toString()
            var senha = txtSenhaCadastrar.text.toString()
            var confirmacaoSenha = txtConfirmacaoSenhaCadastrar.text.toString()

            if (senha.compareTo(confirmacaoSenha) == 0) {
                var task = viewModel.createAuthUsuario(email, senha)

                task.addOnSuccessListener {
                    var result = viewModel.createInfoUsuario(nome, it.user!!.uid)

                    result.addOnSuccessListener {
                        Toast.makeText(
                            requireContext(),
                            "Usuario Cadastrado com sucesso!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    result.addOnFailureListener {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                    result.addOnCompleteListener {
                        FirebaseAuth.getInstance().signOut()
                        findNavController().popBackStack()
                    }
                }
                task.addOnFailureListener {
                    Toast.makeText(
                        requireContext(),
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

            } else {
                Toast.makeText(
                    requireContext(),
                    "Senhas não conferem!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}