package com.capstone.hidroponichy02.model


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map {
            UserModel(
                it[TOKEN_KEY] ?: "",
                it[VERIFY_KEY] ?: false,
                it[USERID_KEY] ?: "",
                it[EMAIL_KEY] ?: "",
                it[NAME_KEY] ?: "",
                it[PASSWORD_KEY] ?: "",
                it[STATE_KEY] ?: false
            )
        }
    }

    suspend fun saveUser(user: UserModel) {
        dataStore.edit {
            it[TOKEN_KEY] = user.token
            it[VERIFY_KEY] = user.verify_user
            it[USERID_KEY] = user.userid
            it[EMAIL_KEY] = user.email
            it[NAME_KEY] = user.fullname
            it[PASSWORD_KEY] = user.password
            it[STATE_KEY] = user.isLogin
        }
    }

    suspend fun logout() {
        dataStore.edit {
            it[TOKEN_KEY] = ""
            it[VERIFY_KEY] = true
            it[USERID_KEY] = ""
            it[EMAIL_KEY] = ""
            it[NAME_KEY] = ""
            it[PASSWORD_KEY] = ""
            it[STATE_KEY] = false
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val TOKEN_KEY = stringPreferencesKey("token")
        private val VERIFY_KEY = booleanPreferencesKey("verify_user")
        private val USERID_KEY = stringPreferencesKey("userid")
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val NAME_KEY = stringPreferencesKey("fullname")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val STATE_KEY = booleanPreferencesKey("state")



        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}