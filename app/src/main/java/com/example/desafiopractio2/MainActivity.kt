package com.example.desafiopractio2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: AuthStateListener

    lateinit var btnIngresar: Button
    lateinit var btnRegistrar:Button

    lateinit var txtEmail: EditText
    lateinit var txtPwd: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Botones
        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnIngresar = findViewById(R.id.btnIngresar)

        // EditText
        txtEmail = findViewById(R.id.editUser)
        txtPwd = findViewById(R.id.editPassword)

        firebaseAuth = Firebase.auth

        // Eventos de Botones
        btnIngresar.setOnClickListener {
            signIn(txtEmail.text.toString(), txtPwd.text.toString())
        }

        btnRegistrar.setOnClickListener{
            val i = Intent(this,CrearCuentaActivity::class.java)
            startActivity(i)
        }
    }

    override fun onBackPressed() {
        return
    }

    private fun signIn(email: String, password: String) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    Toast.makeText(baseContext, user?.uid.toString(), Toast.LENGTH_LONG).show()
                    // Ir a AppActivity
                    val i = Intent(this,AppActivity::class.java)
                    startActivity(i)
                } else {
                    Toast.makeText(baseContext, "Error de email y/o password", Toast.LENGTH_LONG)
                        .show()
                }
            }
    }
}