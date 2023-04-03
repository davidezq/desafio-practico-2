package com.example.desafiopractio2.promedio

data class Alumno(
    var id: String = "",
    var nombre: String = "",
    var notas: List<Double> = listOf(),
    var promedio: Double = 0.0,
    var aprobo: Boolean = false
)