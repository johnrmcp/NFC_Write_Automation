package com.example.em_mattress

import java.io.InputStream

data class Movie(val year: Int,val score: Int,val title: String)

fun readCsv(inputStream: InputStream): List<Movie> {
    val reader = inputStream.bufferedReader()
    val header = reader.readLine()
    return reader.lineSequence()
        .filter { it.isNotBlank() }
        .map {
            val (year, score, title) = it.split(',', ignoreCase = false, limit = 3)
            Movie(year.trim().toInt(), score.trim().toInt(), title.trim().removeSurrounding("\""))
        }.toList()
}
//InputStream input = new FileInputStream("TestingCSV.csv")
//val movies = readCsv(input)
