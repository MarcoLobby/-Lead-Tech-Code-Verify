package com.example.verificacodiceleadtech.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.verificacodiceleadtech.utils.SharedPreferenceStringLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class CodesSharedPreferencesRepository @Inject constructor(val context : Context) {

    private val prefs : SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }

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

    fun codesListLiveData(): LiveData<List<String>> =
        SharedPreferenceStringLiveData(prefs, CODES_LIST, "")
            .map { valueStr ->
                if (valueStr.isEmpty()) {
                    emptyList()
                } else {
                    Gson().fromJson(valueStr, object : TypeToken<List<String>>() {}.type)
                }
            }


    companion object {
        private const val PREFS_FILE_NAME = "codes_prefs"
        private const val CODES_LIST =  "codes_list"
    }
}