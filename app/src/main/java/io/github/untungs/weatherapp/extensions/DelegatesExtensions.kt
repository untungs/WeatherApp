package io.github.untungs.weatherapp.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import kotlin.reflect.KProperty

object DelegatesExt {
    fun <T> notNullSingleValue() = NotNullSingleValueVar<T>()
    fun <T> preference(context: Context, name: String, default: T) = Preferences(context, name, default)
}

class NotNullSingleValueVar<T> {
    private var value: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("${property.name} is not initialized")
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = if (this.value == null) value
        else throw IllegalStateException("${property.name} is already initialized")
    }
}

class Preferences<T>(context: Context, val name: String, val default: T) {
    val prefs: SharedPreferences by lazy { context.getSharedPreferences("default", Context.MODE_PRIVATE) }

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = with(prefs) {
        val res: Any = when (default) {
            is String -> getString(name, default)
            is Boolean -> getBoolean(name, default)
            is Int -> getInt(name, default)
            is Float -> getFloat(name, default)
            is Long -> getLong(name, default)
            else -> throw IllegalArgumentException("This type can't be saved into Preferences")
        }
        res as T
    }

    @SuppressLint("CommitPrefEdits")
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = with(prefs.edit()) {
        when (value) {
            is String -> putString(name, value)
            is Boolean -> putBoolean(name, value)
            is Int -> putInt(name, value)
            is Float -> putFloat(name, value)
            is Long -> putLong(name, value)
            else -> throw IllegalArgumentException("This type can't be saved into Preferences")
        }.apply()
    }
}