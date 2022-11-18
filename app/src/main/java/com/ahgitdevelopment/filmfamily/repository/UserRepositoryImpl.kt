package com.ahgitdevelopment.filmfamily.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserRepository {

    // isAnonymousUser
    override fun isAnonymousUser(): Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences[ANONYMOUS_USER_KEY] ?: true
        }

    override suspend fun setAnonymousUser(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[ANONYMOUS_USER_KEY] = value
        }
    }

    // userName
    override fun getUserName(): Flow<String> =
        dataStore.data.map { preferences ->
            preferences[USER_NAME_KEY] ?: ""
        }

    override suspend fun setUserName(value: String?) {
        dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = value ?: ""
        }
    }

    // userEmail
    override fun getUserEmail(): Flow<String> =
        dataStore.data.map { preferences ->
            preferences[USER_EMAIL_KEY] ?: ""
        }


    override suspend fun setUserEmail(value: String?) {
        dataStore.edit { preferences ->
            preferences[USER_EMAIL_KEY] = value ?: ""
        }
    }


    companion object {
        private val ANONYMOUS_USER_KEY = booleanPreferencesKey("ANONYMOUS_USER")
        private val USER_NAME_KEY = stringPreferencesKey("USER_NAME")
        private val USER_EMAIL_KEY = stringPreferencesKey("USER_EMAIL")
    }
}

interface UserRepository {
    fun isAnonymousUser(): Flow<Boolean>
    suspend fun setAnonymousUser(value: Boolean)

    fun getUserName(): Flow<String>
    suspend fun setUserName(value: String?)

    fun getUserEmail(): Flow<String>
    suspend fun setUserEmail(value: String?)
}