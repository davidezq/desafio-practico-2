package com.example.desafiopractio2.salario

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiopractio2.MainActivity
import com.example.desafiopractio2.R
import com.example.desafiopractio2.promedio.PromedioActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SalarioActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseFirestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salario)

        firebaseAuth = Firebase.auth
        firebaseFirestore = Firebase.firestore

        getEmpleados()

        var btnAgregar = findViewById<Button>(R.id.btnAgregarPersona)
        btnAgregar.setOnClickListener{
            val i = Intent(this,CrearEmpleadoActivity::class.java)
            startActivity(i)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navSalir -> {
                signOut()
            }
            R.id.navPromedio -> {
                val i = Intent(this, PromedioActivity::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        return
    }

    private fun signOut(){
        firebaseAuth.signOut()
        Toast.makeText(baseContext,"Sesión Cerrada", Toast.LENGTH_LONG).show()
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    private fun getEmpleados(){
        var empleados= mutableListOf<Empleado>()

        val email = firebaseAuth.currentUser?.email
        if (email != null) {
            firebaseFirestore.collection("salarios")
                .document(email)
                .collection("empleados")
                .get()
                .addOnSuccessListener { documents ->
                    for(doc in documents){
                        var empleado: Empleado = Empleado()
                        empleado.id = doc.id
                        empleado.nombre = doc.getString("nombre")!!
                        empleado.salarioBase = doc.getDouble("salarioBase")!!
                        empleado.salarioNeto = doc.getDouble("salarioNeto")!!
                        empleados.add(empleado)
                    }

                    val recyclerView = findViewById<RecyclerView>(R.id.recyclerEmpleados)
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    recyclerView.adapter = EmpleadoAdapter(empleados)
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(baseContext, "Error al obtener documentos: $exception",Toast.LENGTH_LONG)
                }
        }
    }
}
























