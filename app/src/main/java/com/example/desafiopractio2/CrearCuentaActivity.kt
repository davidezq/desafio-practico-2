package com.example.desafiopractio2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CrearCuentaActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    // Botones
    lateinit var btnCrearRegistrarse:Button

    // Input Text
    lateinit var editCrearUser:EditText
    lateinit var editCrearPassword:EditText
    lateinit var editCrearConfirmarPassword:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)

        firebaseAuth = Firebase.auth

        // texto de los input text
        editCrearUser = findViewById<EditText>(R.id.editCrearUser)
        editCrearPassword = findViewById<EditText>(R.id.editCrearPassword)
        editCrearConfirmarPassword = findViewById<EditText>(R.id.editCrearConfirmarPassword)

        btnCrearRegistrarse = findViewById<Button>(R.id.btnCrearRegistrarse)

        btnCrearRegistrarse.setOnClickListener{
            var email = editCrearUser.text.toString()
            var pwd1 = editCrearPassword.text.toString()
            var pwd2 = editCrearConfirmarPassword.text.toString()

            if(pwd1 == pwd2){
                crearCuenta(email,pwd1)
            } else{
                Toast.makeText(baseContext,"Contraseñas no coinciden",Toast.LENGTH_LONG).show()
                editCrearPassword.requestFocus()
            }
        }
    }

    private fun crearCuenta(email:String, password:String){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(baseContext,"Usuario creado exitosamente",Toast.LENGTH_LONG).show()
                val i = Intent(this,MainActivity::class.java)
                startActivity(i)
            } else {
                Toast.makeText(baseContext,"Error: ${it.exception?.message}",Toast.LENGTH_LONG).show()
            }
        }
    }
}