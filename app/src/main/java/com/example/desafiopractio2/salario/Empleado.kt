package com.example.desafiopractio2.salario

data class Empleado(
    var id: String = "",
    var nombre: String = "",
    var salarioBase: Double = 0.0,
    var salarioNeto: Double = 0.0
)