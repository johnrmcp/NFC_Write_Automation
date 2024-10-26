package com.example.em_mattress

import android.util.Log
import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReader
import com.opencsv.CSVReaderBuilder
import java.io.File
import java.io.FileReader
import java.io.InputStream

//this function parses the CSV file into a mutable list of lists of strings


fun parseCSV(fileName: File){
    //Convert input stream to file uri
    val csvReader: CSVReader = CSVReaderBuilder(FileReader(fileName)).withCSVParser(CSVParserBuilder().withSeparator(',').build()).build()

    // Read the rest
    var line: Array<String>? = csvReader.readNext()

    while (line != null) {
        // Do something with the data
        Log.d("parseCSV", line[0] + " " + line[1] + " " + line[2])

        line = csvReader.readNext()
    }
}