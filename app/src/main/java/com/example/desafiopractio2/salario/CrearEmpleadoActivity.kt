package com.example.desafiopractio2.salario

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

class CrearEmpleadoActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_empleado)

        firebaseAuth = Firebase.auth
        firebaseFirestore = Firebase.firestore

        findViewById<Button>(R.id.btn_calcular).setOnClickListener {
            submit()
        }
    }

    private fun submit(){
        val nombreEmpleado = findViewById<EditText>(R.id.txt_nombre).text
        val salarioBase = findViewById<EditText>(R.id.txt_salario).text.toString().toDouble()

        val descuentoIsss = salarioBase * 0.03
        val descuentoAfp = salarioBase * 0.04
        val descuentoRenta = salarioBase * 0.05


        val salarioNeto = salarioBase - descuentoIsss - descuentoAfp - descuentoRenta

        firebaseFirestore.collection("salarios")
            .document(firebaseAuth.currentUser?.email!!)
            .collection("empleados")
            .document()
            .set(
                hashMapOf(
                    "nombre" to nombreEmpleado.toString(),
                    "salarioBase" to salarioBase,
                    "salarioNeto" to salarioNeto
                )
            ).addOnSuccessListener{
                Toast.makeText(baseContext, "Empelado $nombreEmpleado guardado", Toast.LENGTH_LONG)
                    .show()
                val i = Intent(this, SalarioActivity::class.java)
                startActivity(i)
            }
    }
}