package com.example.utilisationdesfichiers

class Intervention {

    var numero: String? = null
    var date: String? = null
    var nomPlombier: String? = null
    var typeIntervention: String? = null

    constructor() : super() {}

    constructor(numero: String, date: String, nomPlombier: String, typeIntervention:String) : super() {
        this.numero = numero
        this.date = date
        this.nomPlombier = nomPlombier
        this.typeIntervention = typeIntervention
    }
}