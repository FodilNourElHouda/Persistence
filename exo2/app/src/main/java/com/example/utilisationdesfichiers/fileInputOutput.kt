package com.example.utilisationdesfichiers

import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.io.*


fun readFile(fileName: String , context: Context): String? {
    if (isExternalStorageReadable()) {
        val stringBuilder = StringBuilder()
        try {
            val textFile =
                File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "$fileName.json")
            val fis = FileInputStream(textFile)
            var line = "[]"
            if (fis != null) {
                val isr = InputStreamReader(fis)
                val buff = BufferedReader(isr)

                line = buff.readText()
                fis.close()
            }
            println(line)

            return line


        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    } else
        return ""
}

 fun isExternalStorageReadable(): Boolean {
    return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() ||
        Environment.MEDIA_MOUNTED_READ_ONLY == Environment.getExternalStorageState()

    ) {
        Log.i("state", "Yes it is writable")
        true
    } else {
        false
    }
}


 fun checkPermission(permission: String ,context : Context): Boolean {
    val check = ContextCompat.checkSelfPermission(context, permission)
    return (check == PackageManager.PERMISSION_GRANTED)
}


 fun isExternalStorageWritable(): Boolean {
    return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
        Log.i("state", "Yes it is writable")
        true
    } else {
        false
    }
}

 fun writeFile(data: String, fileName: String , context: Context) {
    if (isExternalStorageWritable()) {
        val textFile = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "$fileName.json")
        textFile.writeText("")

        context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.mkdir()
        try {
            val fos = FileOutputStream(textFile)
            fos.write(data.toByteArray())
            fos.close()
            Toast.makeText(context, "file saved", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Exception", Toast.LENGTH_LONG).show()
        }
    } else {
        Toast.makeText(context, "Cannot write on External storage", Toast.LENGTH_LONG).show()
    }

    println(data)
}