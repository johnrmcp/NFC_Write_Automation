package com.example.em_mattress.CSVfunctions

import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReader
import com.opencsv.CSVReaderBuilder
import java.io.File
import java.io.FileReader


fun parseCSV(fileName: File): MutableList<Order> {
    //this function parses the CSV file into a mutable list of Order objects

    //Read the CSV file and store it to a CSVReader object
    val csvReader: CSVReader = CSVReaderBuilder(FileReader(fileName)).withCSVParser(CSVParserBuilder().withSeparator(',').build()).build()

    // Define the data in the CSV file for each column
    val orderdate: Int = 0
    val ordernumber: Int = 1
    val producttype: Int = 2
    val variant: Int = 3
    val quantity: Int = 4
    val name: List<Int> = listOf(6, 7) //name is spread across multiple columns, 6, 7 are first and last name,
    val address: List<Int> = listOf(8, 9, 10, 11, 13) //address is spread across multiple columns: 8, 9, 10, 11, 13 are add.line1, add.line2, province, city, and country
    val phone: Int = 12
    val image: Int = 14
    val music: Int = 15

    //Create the array of Order (class objects) which we will be filling
    var orderArray = mutableListOf<Order>()

    // Define and read the header line
    var line: Array<String>? = csvReader.readNext()
    line = csvReader.readNext()

    while (line != null) {
        // Create an order object (for each line in the CSV file) and store it in the array
        orderArray.add(
            Order(line[orderdate],
            line[ordernumber],
            line[producttype],
            line[variant],
            line[quantity],
            line[name[0]]+" "+line[name[1]],
            line[address[0]]+" "+line[address[1]]+" "+line[address[2]]+" "+line[address[3]]+" "+line[address[4]],
            line[phone],
            line[image],
            line[music])
        )

        // move to the next line
        line = csvReader.readNext()
    }

    return orderArray
}