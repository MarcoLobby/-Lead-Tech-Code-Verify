package com.example.verificacodiceleadtech.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.verificacodiceleadtech.utils.SharedPreferenceStringLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
//Dentro questo repository gestisco la memorizzazione e il recupero dei codici all'interno delle preferenze condivise dell'applicazione,
//fornendo metodi per accedere ai codici sia in modo sincrono che asincrono tramite LiveData.
class CodesSharedPreferencesRepository @Inject constructor(val context : Context) {

    private val prefs : SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }

    //Lista dei codici memorizzati nelle preferenze condivise
    //Quando richiedo il valore, esso viene letto dalla pref. condiv.
    //e deserializzato utilizzando Gson.
    //Quando imposto un nuovo valore, viene serializzato tramite Gson e
    //salvato nelle pref. condiv.
    var codesList : List <String>
        get(){
            val valueStr = prefs.getString(CODES_LIST, "")
            return if (valueStr.isNullOrEmpty()){
                emptyList()
            } else {
                Gson().fromJson(valueStr, object : TypeToken<List<String>>(){}.type)
            }
        }

        set(value){
            prefs.edit()
                .putString(CODES_LIST, Gson().toJson(value))
                .apply()
        }

    //Questa funzione mi restituisce un oggetto di tipo 'Livedata'
    //che rappresenta la lista dei codici memorizzati nelle pref. condiv.
    //Tramite la sua classe personalizzata 'Shared..StringLiveData' osservo
    //i cambiamenti nelle pref. condiv. e mappo il valore in una lista di codici tramite Gson.
    fun codesListLiveData(): LiveData<List<String>> =
        SharedPreferenceStringLiveData(prefs, CODES_LIST, "")
            .map { valueStr ->
                if (valueStr.isEmpty()) {
                    emptyList()
                } else {
                    Gson().fromJson(valueStr, object : TypeToken<List<String>>() {}.type)
                }
            }

    //costanti per identificare i file di pref. condiv. e
    //la chiave per la lista dei codici all'interno delle pref.
    companion object {
        private const val PREFS_FILE_NAME = "codes_prefs"
        private const val CODES_LIST =  "codes_list"
    }
}