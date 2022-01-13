package com.example.aplicacion

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroPantalla  : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_registro)
    }
    fun Return(view:android.view.View){
        onBackPressed();
    }

    fun setBottom(){
        val Nombre : EditText = findViewById(R.id.editNombre);
        val Apellido : EditText = findViewById(R.id.editApellidos);
        val fecha: EditText = findViewById(R.id.editFecha);
        val Email : EditText = findViewById(R.id.editEmailRegistro);
        val Passwd : EditText = findViewById(R.id.editContraseña1);
        val Passwd2 : EditText = findViewById(R.id.editContraseña2);


        db.collection("clientes").document(Email.text.toString()).set(hashMapOf(
            "Nombre" to Nombre.text.toString(),
            "Apellido" to Apellido.text.toString(),
            "fecha" to fecha.text.toString(),
            "Passwd" to Passwd.text.toString(),
            "Passwd2" to Passwd2.text.toString()
        ))
    }
    private fun irDosPantalla() {
        val dosPantallaIntent = Intent(this, DosPantalla::class.java)
        startActivity(dosPantallaIntent)


    }

    private fun showError() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error con firebase")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun onClickConfirmar(view: android.view.View) {

        val email: EditText = findViewById(R.id.editEmailRegistro);
        val cont: EditText = findViewById(R.id.editContraseña1);
        val cont2: EditText = findViewById(R.id.editContraseña2)

        if (email != null && cont != null && cont2!=null) {
            if (email.text.toString() != "" && cont.text.toString() != "") {
                var emailString = email.text.toString()
                var contString = cont.text.toString()
                var cont2String = cont2.text.toString()

                if ( contString == cont2String) {

                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(emailString, contString)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                setBottom()
                                irDosPantalla()

                            } else {
                                //Error
                                showError()
                            }
                        }


                }else{
                    showError()
                }
            }
        } else {

        }
        //Se realizara cuando se presione el boton NEXT

        //Intent es una clase que recibe 2 parametros, donde estamos y a donde vamos

        //Creamos un objeto de la clase Intent


    }
}