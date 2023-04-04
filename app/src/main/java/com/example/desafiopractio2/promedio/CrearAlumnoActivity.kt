package com.example.desafiopractio2.promedio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.desafiopractio2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CrearAlumnoActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseFirestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_alumno)

        firebaseAuth = Firebase.auth
        firebaseFirestore = Firebase.firestore

        findViewById<Button>(R.id.btn_calcular).setOnClickListener { submit() }
    }

    private fun submit() {
        val nombreEstudiante = findViewById<EditText>(R.id.edtEditarAlumnoNombre).text
        var notas = DoubleArray(5)
        for (i in 0..notas.size - 1) {
            notas[i] = findViewById<EditText>(
                resources.getIdentifier(
                    "txt_nota${i + 1}",
                    "id",
                    packageName
                )
            ).text.toString().toDouble()
            if (notas[i] > 10) {
                Toast.makeText(this, "Revise las notas si son correctas", Toast.LENGTH_LONG).show()
                return
            }
        }
        var promedio = notas.sum() / notas.size

        firebaseFirestore.collection("promedios")
            .document(firebaseAuth.currentUser?.email!!)
            .collection("items")
            .document()
            .set(
                hashMapOf(
                    "nombre" to nombreEstudiante.toString(),
                    "notas" to (notas.asList()),
                    "promedio" to promedio,
                    "aprobado" to (promedio >= 6)
                )
            ).addOnSuccessListener {
                Toast.makeText(baseContext, "Alumno $nombreEstudiante guardado", Toast.LENGTH_LONG)
                    .show()
                val i = Intent(this, PromedioActivity::class.java)
                startActivity(i)
            }
    }

}