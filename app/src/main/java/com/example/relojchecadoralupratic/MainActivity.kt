package com.example.relojchecadoralupratic

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.relojchecadoralupratic.databinding.ActivityMainBinding
import com.example.relojchecadoralupratic.ui.activities.LoginActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)

        // Verificar si el usuario ha iniciado sesión
        if (!isLoggedIn()) {
            // El usuario no ha iniciado sesión, redirigir a la pantalla de inicio de sesión
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Finalizar la actividad actual para evitar que el usuario regrese presionando el botón Atrás
            return // Salir del método onCreate para evitar que se ejecute el código restante
        }

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Configurar la barra de acción con el controlador de navegación
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_inicio, R.id.nav_gestion_empleados, R.id.nav_reportes
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Configurar NavigationView para que use el controlador de navegación
        navView.setupWithNavController(navController)
    }

    // Función para verificar si el usuario ha iniciado sesión
    private fun isLoggedIn(): Boolean {
        // Obtener el estado de inicio de sesión almacenado en SharedPreferences
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        val username = sharedPreferences.getString("username", "N/A")
        Log.d("MainActivity", "Usuario en sesión: $username")
        return isLoggedIn
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Verificar si el usuario ha iniciado sesión
        if (!isLoggedIn()) {
            // El usuario no ha iniciado sesión, redirigir a la pantalla de inicio de sesión
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Finalizar la actividad actual para evitar que el usuario regrese presionando el botón Atrás
            return false // Salir del método onCreateOptionsMenu para evitar que se ejecute el código restante
        }

        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        // Permite que la barra de acción maneje el comportamiento de navegación
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
