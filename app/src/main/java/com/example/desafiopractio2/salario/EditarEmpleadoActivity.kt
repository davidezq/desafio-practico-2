package com.example.desafiopractio2.salario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.desafiopractio2.R
import com.example.desafiopractio2.promedio.PromedioActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditarEmpleadoActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseFirestore: FirebaseFirestore

    lateinit var nombreEmpleado: EditText
    lateinit var salarioBase: EditText

    lateinit var btnEditar: Button
    lateinit var btnBorrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_empleado)

        firebaseAuth = Firebase.auth
        firebaseFirestore = Firebase.firestore

        nombreEmpleado = findViewById(R.id.txt_nombre)
        salarioBase = findViewById(R.id.txt_salario_editar)

        btnEditar = findViewById(R.id.btn_editar)
        btnBorrar = findViewById(R.id.btn_eliminar)

        nombreEmpleado.setText(intent.getStringExtra("nombre"))
        salarioBase.setText(intent.getStringExtra("salarioBase").toString())

        btnEditar.setOnClickListener {
            submit()
        }

        btnBorrar.setOnClickListener {
            delete()
        }
    }

    private fun submit() {
        if (nombreEmpleado.text.isEmpty()){
            Toast.makeText(this, "Nombre no puede quedar vacio", Toast.LENGTH_LONG).show()
            return
        }

        val nombre = nombreEmpleado.text.toString()

        if(salarioBase.text.isEmpty()){
            Toast.makeText(this, "Salario no puede quedar vacio", Toast.LENGTH_LONG).show()
            return
        }

        val salarioBase = salarioBase.text.toString().toDouble()

        val descuentoIsss = salarioBase * 0.03
        val descuentoAfp = salarioBase * 0.04
        val descuentoRenta = salarioBase * 0.05


        val salarioNeto = salarioBase - descuentoIsss - descuentoAfp - descuentoRenta

        firebaseFirestore.collection("salarios")
            .document(firebaseAuth.currentUser?.email!!)
            .collection("empleados")
            .document(intent.getStringExtra("id")!!)
            .set(
                hashMapOf(
                    "nombre" to nombre,
                    "salarioBase" to salarioBase,
                    "salarioNeto" to salarioNeto
                )
            ).addOnSuccessListener {
                Toast.makeText(baseContext,"Empleado $nombre editado", Toast.LENGTH_LONG).show()
                val i = Intent(this, SalarioActivity::class.java)
                startActivity(i)
            }
    }

    private fun delete() {
        firebaseFirestore.collection("salarios")
            .document(firebaseAuth.currentUser?.email!!)
            .collection("empleados")
            .document(intent.getStringExtra("id")!!)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(
                    baseContext,
                    "Empleado ${intent.getStringExtra("nombre")!!} eliminado",
                    Toast.LENGTH_LONG
                ).show()
                val i = Intent(this, SalarioActivity::class.java)
                startActivity(i)
            }
    }
}