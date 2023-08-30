package com.example.movieapp

import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

fun request(method: String, url: String, header: Map<String,String>): String? {
    var connection: HttpURLConnection? = null
    var reader: BufferedReader? = null
    var result: String? = null

    try {
        val url = URL(url)
        connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        for ((k, v) in header) {
            Log.d("Header", "$k $v")
            connection.setRequestProperty(k, v)
        }
        connection.connect()

        val inputStream = connection.inputStream
        val buffer = StringBuilder()

        reader = BufferedReader(InputStreamReader(inputStream))
        var line: String?

        while (reader.readLine().also { line = it } != null) {
            buffer.append(line).append("\n")
        }

        result = buffer.toString()
        Log.d("Result", result?.toString() ?: "Result is empty")
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        connection?.disconnect()
        reader?.close()
    }
    return result
}