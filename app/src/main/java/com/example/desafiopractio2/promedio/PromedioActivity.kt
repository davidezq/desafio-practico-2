package com.example.desafiopractio2.promedio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiopractio2.CrearCuentaActivity
import com.example.desafiopractio2.MainActivity
import com.example.desafiopractio2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class PromedioActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promedio)

        firebaseAuth = Firebase.auth
        db = FirebaseFirestore.getInstance()

        getAlumnos()

        var btnAgregar = findViewById<Button>(R.id.btnAgregarAlumno)
        btnAgregar.setOnClickListener{
            val i = Intent(this,CrearAlumnoActivity::class.java)
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
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        return
    }

    private fun signOut(){
        firebaseAuth.signOut()
        Toast.makeText(baseContext,"Sesión Cerrada",Toast.LENGTH_LONG).show()
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    private fun getAlumnos(){


        var alumnos= mutableListOf<Alumno>()

        val email = firebaseAuth.currentUser?.email
        if (email != null) {
            db.collection("promedios")
                .document(email)
                .collection("items")
                .get()
                .addOnSuccessListener { documents ->
                    for(doc in documents){
                        var alumno:Alumno = Alumno()
                        alumno.id = doc.id
                        alumno.nombre = doc.getString("nombre")!!
                        alumno.notas = doc.get("notas") as List<Double>
                        alumno.promedio = doc.getDouble("promedio")!!
                        alumno.aprobo = doc.get("aprobado") as Boolean
                        alumnos.add(alumno)
                    }

                    val recyclerView = findViewById<RecyclerView>(R.id.recyclerAlumnos)
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    recyclerView.adapter = AlumnoAdapter(alumnos)
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(baseContext, "Error al obtener documentos: $exception",Toast.LENGTH_LONG)
                }
        }
    }


}