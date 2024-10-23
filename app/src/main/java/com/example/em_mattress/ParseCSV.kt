package com.example.em_mattress

import com.opencsv.CSVReader
import java.io.FileReader


//fun functionName(parameterName: parameterType, parameter2Name: parameter2Type):  returnVariableType{ contents}
//this function parses the CSV file into a mutable list of lists of strings

fun parseCSV(filePath: String): List<List<String>> {
    val reader = CSVReader(FileReader(filePath))
    val data: MutableList<List<String>> = mutableListOf()
    reader.forEach { row ->
        data.add(row.toList())
    }
    reader.close()
    println("message")
    println(data)
    return data
}

/*
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
*/