package com.cham.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.mutablePreferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2022/1/25 18:00
 * @UpdateUser:
 * @UpdateDate:     2022/1/25 18:00
 * @UpdateRemark:
 */
object DataStoreManager {

    /**
     * val/var <属性名>:<类型> by <表达式>
     * */
    //扩展属性  属性委托
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cham_datastore")

    val NETEASE = stringPreferencesKey("netease")

    val NETEASE_COUNT = intPreferencesKey("net_ease")

    private val cache = mutablePreferencesOf()


}


