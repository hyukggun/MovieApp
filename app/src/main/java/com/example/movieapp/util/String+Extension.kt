package com.example.movieapp.util

import com.google.gson.Gson
import java.lang.Exception

inline fun <reified T> String?.decode(): T? {
    return try {
        Gson().fromJson(this, T::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}