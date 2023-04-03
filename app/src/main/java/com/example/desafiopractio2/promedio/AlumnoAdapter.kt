package com.example.desafiopractio2.promedio

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiopractio2.R

class AlumnoAdapter(val alumnosList:List<Alumno>) : RecyclerView.Adapter<AlumnoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AlumnoViewHolder(layoutInflater.inflate(R.layout.item_alumno,parent,false))

    }

    override fun onBindViewHolder(holder: AlumnoViewHolder, position: Int) {
        val item = alumnosList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return alumnosList.size
    }

}