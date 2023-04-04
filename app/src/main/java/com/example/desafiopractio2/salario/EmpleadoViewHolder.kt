package com.example.desafiopractio2.salario

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.desafiopractio2.R

class EmpleadoViewHolder(val view: View) : ViewHolder(view) {

    val empleado = view.findViewById<TextView>(R.id.tvPersona)
    val salario = view.findViewById<TextView>(R.id.tvSalario)
    val salarioNeto = view.findViewById<TextView>(R.id.tvSalarioNeto)

    fun render(empleadoModel: Empleado){
        empleado.text = empleadoModel.nombre
        salario.text = "Salario: " + empleadoModel.salarioBase.toString()
        salarioNeto.text = "Salario neto:" + empleadoModel.salarioNeto.toString()

        itemView.setOnClickListener{
            val i = Intent(view.context,EditarEmpleadoActivity::class.java)
            i.putExtra("id", empleadoModel.id)
            i.putExtra("nombre",empleadoModel.nombre)
            i.putExtra("salarioBase",empleadoModel.salarioBase.toString())
            view.context.startActivity(i)
        }
    }
}