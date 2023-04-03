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

class EditarAlumnoActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseFirestore: FirebaseFirestore

    lateinit var nombreAlumno: EditText
    lateinit var nota1: EditText
    lateinit var nota2: EditText
    lateinit var nota3: EditText
    lateinit var nota4: EditText
    lateinit var nota5: EditText

    lateinit var btnEditar: Button
    lateinit var btnBorrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_alumno)

        firebaseAuth = Firebase.auth
        firebaseFirestore = Firebase.firestore

        nombreAlumno = findViewById(R.id.edtEditarAlumnoNombre)
        nota1 = findViewById(R.id.txtEditarAlumnoNota1)
        nota2 = findViewById(R.id.txtEditarAlumnoNota2)
        nota3 = findViewById(R.id.txtEditarAlumnoNota3)
        nota4 = findViewById(R.id.txtEditarAlumnoNota4)
        nota5 = findViewById(R.id.txtEditarAlumnoNota5)

        btnEditar = findViewById(R.id.btnEditarAlumnoEditar)
        btnBorrar = findViewById(R.id.btnEditarAlumnoBorrar)

        nombreAlumno.setText(intent.getStringExtra("nombre"))
        nota1.setText(intent.getStringExtra("nota1"))
        nota2.setText(intent.getStringExtra("nota2"))
        nota3.setText(intent.getStringExtra("nota3"))
        nota4.setText(intent.getStringExtra("nota4"))
        nota5.setText(intent.getStringExtra("nota5"))


        btnEditar.setOnClickListener {
            submit()
        }

        btnBorrar.setOnClickListener {
            delete()
        }
    }

    private fun submit() {
        val nombreEstudiante = nombreAlumno.text.toString()
        var notas = DoubleArray(5)
        for (i in 0..notas.size - 1) {
            notas[i] = findViewById<EditText>(
                resources.getIdentifier(
                    "txtEditarAlumnoNota${i + 1}",
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
            .document(intent.getStringExtra("id")!!)
            .set(
                hashMapOf(
                    "nombre" to nombreEstudiante,
                    "notas" to (notas.asList()),
                    "promedio" to promedio,
                    "aprobado" to (promedio >= 6)
                )
            ).addOnSuccessListener {
                Toast.makeText(baseContext,"Alumno $nombreEstudiante editado",Toast.LENGTH_LONG).show()
                val i = Intent(this,PromedioActivity::class.java)
                startActivity(i)
            }
    }

    private fun delete() {
        firebaseFirestore.collection("promedios")
            .document(firebaseAuth.currentUser?.email!!)
            .collection("items")
            .document(intent.getStringExtra("id")!!)
            .delete()
            .addOnSuccessListener {
                val i = Intent(this,PromedioActivity::class.java)
                startActivity(i)
            }
    }
}