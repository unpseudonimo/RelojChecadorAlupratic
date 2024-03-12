package com.example.relojchecadoralupratic

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.relojchecadoralupratic.databinding.ActivityMainBinding
import com.example.relojchecadoralupratic.ui.activities.LoginActivity
import com.example.relojchecadoralupratic.ui.fragments.gestionempleados.AddEditEmpleadoFragment
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
        supportActionBar?.show()  // Agrega esta línea

        /*binding.appBarMain.fab.setOnClickListener { view ->
            val navController = findNavController(R.id.nav_host_fragment_content_main)
            navController.navigate(R.id.nav_gestion_empleados)
        }*/

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Configurar la barra de acción con el controlador de navegación
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_inicio, R.id.nav_gestion_empleados, R.id.nav_reportes,R.id.nav_gestion_asistencias
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Configurar NavigationView para que use el controlador de navegación
        navView.setupWithNavController(navController)

        // Obtener el TextView del header y establecer el nombre de usuario
        val headerView = navView.getHeaderView(0)
        val textViewUsername = headerView.findViewById<TextView>(R.id.textView)
        textViewUsername.text = sharedPreferences.getString("username", "N/A")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                showLogoutConfirmationDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Función para verificar si el usuario ha iniciado sesión
    private fun isLoggedIn(): Boolean {
        // Obtener el estado de inicio de sesión almacenado en SharedPreferences
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        val username = sharedPreferences.getString("username", "N/A")
        Log.d("MainActivity", "Usuario en sesión: $username")
        return isLoggedIn
        Log.d("MainActivity",  "$isLoggedIn")
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
    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Cerrar sesión")
            .setMessage("¿Estás seguro de que quieres cerrar sesión?")
            .setPositiveButton("Sí") { dialog, which ->
                logout()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun logout() {
        // Limpiar las credenciales de inicio de sesión en SharedPreferences
        sharedPreferences.edit().apply {
            remove("isLoggedIn")
            remove("username")
            apply()
        }
        // Mostrar Toast de cierre de sesión
        Toast.makeText(this, "Sesión cerrada exitosamente", Toast.LENGTH_SHORT).show()
        // Redirigir al usuario a la pantalla de inicio de sesión
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Finalizar la actividad actual para evitar que el usuario regrese presionando el botón Atrás
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
