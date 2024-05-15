package com.example.verificacodiceleadtech.utils

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import androidx.lifecycle.LiveData


//questa classe astratta mi fornisce un modo conveniente per osservare e reagire ai cambiamenti
//nelle preferenze condivise dell'applicazione utilizzando LiveData e
//facilitando l'aggiornamento dell'interfaccia utente in base alle modifiche delle preferenze.
abstract class SharedPreferenceLiveData<T>(
    var sharedPrefs: SharedPreferences,
    var key: String,
    var defValue: T
) : LiveData<T>() {
    private val preferenceChangeListener =
    //Inizializzo Listener per le pref. condiv.
        //che verrà chiamato quando la pref. specificata cambia
        OnSharedPreferenceChangeListener { _, key ->
            if (this@SharedPreferenceLiveData.key == key) {
                value = getValueFromPreferences(key, defValue)
            }
        }

    //Questo metodo è responsabile di ottenere il valore attuale
    // della preferenza condivisa specificata dalla chiave fornita.
    abstract fun getValueFromPreferences(key: String, defValue: T): T

    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences(key, defValue)
        sharedPrefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }
}