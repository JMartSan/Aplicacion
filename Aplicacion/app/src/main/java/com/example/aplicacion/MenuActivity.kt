package com.example.aplicacion

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacion.databinding.ActivityMenuBinding
import com.google.firebase.auth.FirebaseAuth

class MenuActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMenu.toolbar)

       /* binding.appBarMenu.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
           R.id.nav_home,R.id.nav_profile,R.id.nav_task,R.id.nav_chat,R.id.nav_plan,R.id.nav_booking,R.id.nav_notifications,R.id.nav_help,R.id.nav_exit), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Al crearse cambiamos los datos que queramos
        val email = intent.getStringExtra("email")
        val nombre = intent.getStringExtra("nombre")
        if(email != "" && nombre != ""){
            cambiarNombreMenuInflable(email.toString(), nombre.toString())
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_menu)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun cambiarNombreMenuInflable(email : String, nombre : String){
        // Recuperamos los datos de Firebase.

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        val hView: View = navigationView.getHeaderView(0)
        val emailCaja: EditText= hView.findViewById(R.id.emailFlotanteId)
        emailCaja.setText(email)
        val nombreCaja: EditText = hView.findViewById(R.id.nombreFlotanteId)
        nombreCaja.setText(nombre)
    }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
       val id= item.itemId
       if(id==R.id.action_settings){
           FirebaseAuth.getInstance().signOut()
           val nextIntent= Intent(this, DosPantalla::class.java)
           intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
           startActivity(nextIntent)
           finish()
       }
        return super.onOptionsItemSelected(item)
    }
/*
    private fun logout() {

        FirebaseAuth.getInstance().signOut()
        val volverIntent = Intent(this, DosPantalla::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(volverIntent)
        finish()
    }*/


}
