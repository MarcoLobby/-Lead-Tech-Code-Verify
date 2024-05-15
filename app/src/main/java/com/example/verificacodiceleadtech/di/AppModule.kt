package com.example.verificacodiceleadtech.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
//l'annotazione module indica che la mia classe AppModule
//è un modulo Dagger, ovvero che fornisce dipendenze per la mia app

@InstallIn(SingletonComponent::class)
//Questa annotazione mi specifica che il modulo è installato nel
//"SingletonComponent", ovvero il livello di visibilità delle dipendenze
//fornite dal modulo

class AppModule {

    @Singleton
    //Questa annotazione specifica che il metodo bindContext
    //fornisce una singola istanza di Context all'interno dell'applicazione,
    //garantendo che venga condiviso tra tutte le classi che lo richiedono.
    @Provides
    fun bindContext(application: Application):Context{
        return application

        // Questo metodo accetta un oggetto Application e restituisce un oggetto Context.
        // Il contesto fornito è l'applicazione stessa. Questo metodo viene utilizzato per fornire un'istanza di Context
        // all'interno dell'applicazione utilizzando Dagger
    }

}