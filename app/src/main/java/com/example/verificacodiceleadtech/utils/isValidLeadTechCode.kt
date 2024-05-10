package com.example.verificacodiceleadtech.utils

fun isValidLeadTechCode(code: String): Boolean {

    // Rimuovo i trattini dal codice
    val codeWithoutHyphens = code.replace("-", "")
    // Controllo se la lunghezza del codice ha la lunghezza corretta
    if (codeWithoutHyphens.length != 10) {
        return false
    }
    // Estraggo i singoli caratteri del codice e li mappo
    val digits = codeWithoutHyphens.map {
        if (it == 'X') {
            10
        } else {
            it.toString().toInt()
        }
    }
    // Calcolo la somma ponderata dei caratteri
    var weightedSum = 0
    for (i in 0..9) {
        weightedSum += digits[i] * (10 - i)
    }
    // Calcolo il risultato della somma ponderata modulo di 11
    val remainder = weightedSum % 11
    // Controllo se il risultato è uguale a 0
    return remainder == 0
}





