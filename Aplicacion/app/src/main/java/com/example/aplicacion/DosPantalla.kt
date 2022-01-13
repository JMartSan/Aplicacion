package com.example.aplicacion




import android.content.Context
import android.content.Intent
import android.os.Bundle



import android.view.View
import android.widget.EditText
import android.widget.ImageButton



import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity



import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException

import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.firebase.ui.auth.util.ui.PreambleHandler.setup


import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.android.gms.common.api.ApiException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FacebookAuthProvider

import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.auth.GoogleAuthProvider


class DosPantalla : AppCompatActivity() {
    private val GOOGLE_SIGN_IN=100

    private val callbackManager = CallbackManager.Factory.create()


    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)
        setTheme(R.style.Theme_Aplicacion)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_dos)
        //Facebook
        // ...
        //Setup
       // FirebaseAuth.getInstance().signOut()
        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val registerFacebook : ImageButton = findViewById(R.id.facebookButton);
         registerFacebook.setOnClickListener{
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult?) {
                        result?.let {
                            val token = it.accessToken
                            val credential = FacebookAuthProvider.getCredential(token.token)

                            FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                                if(it.isSuccessful){
                                    irMenuActivity()

                                } else {
                                    showError()
                                }
                            }
                        }

                    }

                    override fun onCancel() {
                        TODO("Not yet implemented")

                    }

                    override fun onError(error: FacebookException?) {
                        showError()
                    }
                })

         }




        //Google
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        checkSession()
        bundle.putString("message", "Firebase tutorial")
        analytics.logEvent("InitScreen", bundle)

        google()
    }
    private fun google(){
        val googleConf = GoogleSignInOptions.
        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
        requestIdToken(getString(R.string.default_web_client_id))
            .
            requestEmail().
            build()
        val gClient = GoogleSignIn.getClient(this,
            googleConf)
        val registerButton : ImageButton = findViewById(R.id.bottonGoogle);
        registerButton.setOnClickListener{
            gClient.signOut()
            val intent = gClient.getSignInIntent()
            startActivityForResult(intent, 100)
        }

    }


    private fun checkSession() {
        val preferences =
            getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE)
        val email = preferences.getString("email", null)
        if (email != null) {
            irMenuActivity()
        }

    }

    private fun irMenuActivity() {
        val menuActivityIntent = Intent(this, MenuActivity::class.java)
        startActivity(menuActivityIntent)

    }
    private fun irMenuActivity(email : String, nombre : String) {
        val menuActivityIntent = Intent(this, MenuActivity::class.java)
        menuActivityIntent.apply {
            putExtra("email", email).
            putExtra("nombre", nombre)
        }

        startActivity(menuActivityIntent)


    }


    private fun showError() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error con firebase")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun onClickEntrar(view: View) {
        val email: EditText = findViewById(R.id.editEmail);
        val passw: EditText = findViewById(R.id.editTextTextPassword2);

        if (email.text.isNotEmpty() && passw.text.isNotEmpty()) {

            try {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email.text.toString(), passw.text.toString())
                    .addOnCompleteListener() {
                        if (it.isSuccessful) {

                            irMenuActivity()
                        } else {
                            showError()
                        }
                    }
            } catch (ex: Exception) {

                showError()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        callbackManager.onActivityResult(requestCode,resultCode,data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener() {

                            if (it.isSuccessful) {
                                irMenuActivity(account.email, account.displayName)
                            } else {

                            }
                        }
                }
            } catch (e: ApiException) {

                val builder = AlertDialog.Builder(this)
                builder.setTitle("La contraseña o el email es incorrecto")
                builder.setMessage(e.message)
                builder.setPositiveButton("Aceptar", null)
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }

    fun onClickRegistrar(view: android.view.View) {
        val inicioIntent = Intent(this, RegistroPantalla::class.java)
        startActivity(inicioIntent)
    }

}