package br.pro.nigri.projetoblocoandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class CotacoesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cotacoes)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView2)

        val navController = findNavController(R.id.fragmentCotacoes)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.listaMoedasFragment,R.id.calculadoraConversaoFragment,R.id.favoritosFragment,R.id.contaFragment))
        setupActionBarWithNavController(navController,appBarConfiguration)

        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val navController = findNavController(R.id.fragmentCotacoes)

        navController.popBackStack()

        return super.onOptionsItemSelected(item)
    }
}