package com.example.desafiopractio2.salario

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiopractio2.R

class EmpleadoAdapter(private val empleadoList:List<Empleado>) : RecyclerView.Adapter<EmpleadoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpleadoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return EmpleadoViewHolder(layoutInflater.inflate(R.layout.item_persona,parent,false))
    }

    override fun onBindViewHolder(holder: EmpleadoViewHolder, position: Int) {
        val item = empleadoList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int {
        return empleadoList.size
    }
}