package com.example.verificacodiceleadtech.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.verificacodiceleadtech.repository.CodesSharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repo: CodesSharedPreferencesRepository) : ViewModel() {
//Il mio costruttore accetta un'istanza di 'CodesSharedPreferencesRepository' come dipendenza
    //che viene fornita da Dagger grazie all'annotazione '@Inject'

    fun getList(): LiveData<List<String>> {
        return repo.codesListLiveData()
    }

    //Salvo l'item nella lista
    fun saveList(item: String) {
        repo.codesList = repo.codesList.plus(item)
    }

    //Al click del tasto "rimuovi" la lista dei codici viene aggiornata eliminato il codice desiderato
    fun removeCode(item: String) {
        repo.codesList = repo.codesList.toMutableList().apply { remove(item) }
    }
}