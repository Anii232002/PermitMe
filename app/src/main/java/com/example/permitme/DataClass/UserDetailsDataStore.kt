package com.example.permitme.DataClass

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore:DataStore<Preferences> by preferencesDataStore(name = "user_details")
private val USER_EMAIL= stringPreferencesKey("user_email")
private val USER_VALUE= intPreferencesKey("user_value")

class UserDetailsDataStore(context: Context) {
    suspend fun writeEmail(email:String, context: Context){
        context.dataStore.edit {

            preferencesEmail->
            Log.d("InsideWriteDetails","Inside"+email)
            preferencesEmail[USER_EMAIL]=email

        }
    }
    suspend fun writeAmount(amount:Int,context: Context){
        context.dataStore.edit {

            preferencesAmount->
            Log.d("InsideWriteDetails","Inside"+amount)
            preferencesAmount[USER_VALUE]=amount

        }
    }

    val preferenceEmailFlow: Flow<String> =context.dataStore.data.map {
        preferencesEmail->
        preferencesEmail[USER_EMAIL]!!
    }

    val preferencesAmountValue:Flow<Int> = context.dataStore.data.map {
        preferencesAmount->
        preferencesAmount[USER_VALUE]!!
    }
}