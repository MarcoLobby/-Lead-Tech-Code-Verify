package com.example.verificacodiceleadtech.utils

import android.content.SharedPreferences


//Questa classe estende 'SharedPreferenceLiveData' e mi fornisce un meccanismo specifico
//per osservare e reagire ai cambiamenti in una preferenza condivisa di tipo stringa.
//Mi serve per monitorare dinamicamente il valore di una preferenza condivisa di tipo stringa
//e aggiornare l'interfaccia utente in base a eventuali modifiche.
class SharedPreferenceStringLiveData(prefs: SharedPreferences, key: String, defValue: String) :
    SharedPreferenceLiveData<String>(prefs, key, defValue) {
    override fun getValueFromPreferences(key: String, defValue: String): String {
        return sharedPrefs.getString(key, defValue).orEmpty()
    }
}