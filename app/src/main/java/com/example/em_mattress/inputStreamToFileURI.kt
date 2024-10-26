package com.example.em_mattress

import android.content.Context
import android.os.FileUtils.copy
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

@Throws(IOException::class)
private fun trap(source: InputStream, target: OutputStream) {
    val buf = ByteArray(8192)
    var length: Int
    while (source.read(buf).also { length = it } > 0) {
        target.write(buf, 0, length)
    }
}

fun inputStreamToFileURI(context: Context, inputstream: InputStream): File {
    val fileName = "temp_file.csv"

    // Creating Temp file
    val tempFile = File(context.cacheDir, fileName)
    tempFile.createNewFile()

    try {
        val oStream = FileOutputStream(tempFile)
        inputstream.let {
            trap(inputstream, oStream)
        }
        oStream.flush()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return tempFile
}