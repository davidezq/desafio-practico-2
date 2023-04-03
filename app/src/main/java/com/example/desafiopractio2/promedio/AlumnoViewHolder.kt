package com.example.desafiopractio2.promedio

import android.content.Intent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.desafiopractio2.R

class AlumnoViewHolder(val view: View) : ViewHolder(view) {

    val alumno = view.findViewById<TextView>(R.id.tvAlumno)
    val promedio = view.findViewById<TextView>(R.id.tvPromedio)
    val aprobo = view.findViewById<TextView>(R.id.tvAprobado)

    fun render(alumnoModel: Alumno) {
        alumno.text = "Alumno:" + alumnoModel.nombre
        promedio.text = "Promedio: " + alumnoModel.promedio.toString()
        aprobo.text = "${if (alumnoModel.aprobo)"Aprobado 🎉" else "Reprobado 😔"}"

        itemView.setOnClickListener{
            val i = Intent(view.context,EditarAlumnoActivity::class.java)
            i.putExtra("id", alumnoModel.id.toString())
            i.putExtra("nombre", alumnoModel.nombre.toString())
            i.putExtra("nota1", alumnoModel.notas[0].toString())
            i.putExtra("nota2", alumnoModel.notas[1].toString())
            i.putExtra("nota3", alumnoModel.notas[2].toString())
            i.putExtra("nota4", alumnoModel.notas[3].toString())
            i.putExtra("nota5", alumnoModel.notas[4].toString())
            view.context.startActivity(i)
        }
    }
}