package com.example.utilisationdesfichiers

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_ajouter_intervention.*
import org.json.JSONArray
import java.io.*
import java.util.jar.Manifest

@Suppress("DEPRECATION")
class AjouterIntervention : AppCompatActivity() {

    lateinit var nomPlombier: Spinner
    val nomList = arrayListOf<String>("Mohamed", "islam", "samir", "reda", "toufik")
    lateinit var typeIntervention: Spinner
    val typeList = arrayListOf<String>("type1", "type2", "type3", "type4")
    lateinit var dateIntervention: Button
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_intervention)





        //writeFile(jsonString, "file")


        nomPlombier = findViewById(R.id.nom_plombier)
        typeIntervention = findViewById(R.id.typeIntervention)
        val nomAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nomList)
        nomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        nomPlombier.adapter = nomAdapter

        val typeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, typeList)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeIntervention.adapter = typeAdapter

        dateIntervention = findViewById<Button>(R.id.date_intervention)

        val datePicker = DatePicker(this)

        val button = Button(this)
        button.background.setTint(Color.parseColor("#2196F3"))
        button.setTextColor(Color.parseColor("#FFFFFF"))
        button.text = "valider"
        button.gravity = Gravity.CENTER
        var datePickerValue = ""
        button.setOnClickListener {
            datePickerValue = "${datePicker.dayOfMonth}/${datePicker.month+1}/${datePicker.year}"
            dateIntervention.text = datePickerValue
            linear_layout.visibility = View.GONE
        }

        linear_layout?.addView(datePicker)
        linear_layout?.addView(button)
        dateIntervention.setOnClickListener {

            linear_layout.visibility = View.VISIBLE
        }
        ajouter.setOnClickListener {
            val listIntervention = arrayListOf<Intervention>()
            try {
                val json = JSONArray(readFile("file" , this))
                var i = json.length() - 1

                while (i >= 0) {

                    val jsonIntervention = json.getJSONObject(i)

                    listIntervention.add(
                        Intervention(jsonIntervention["numero"].toString(),
                            jsonIntervention["date"].toString(),
                            jsonIntervention["nomPlombier"].toString(),
                            jsonIntervention["typeIntervention"].toString())
                    )
                    i--
                }
            }catch (e : NullPointerException)
            {
                e.printStackTrace()
            }

            listIntervention.add(
                Intervention(
                    numero_intervention.text.toString(),
                    datePickerValue ,
                    nomPlombier.selectedItem.toString(),
                    typeIntervention.selectedItem.toString()

                )
            )
            var gson = Gson()
            val file = File.createTempFile("data", ".json")
            var jsonString: String = gson.toJson(listIntervention)
            writeFile(jsonString , "file" , this)
            Toast.makeText(this , "Element ajouté" , Toast.LENGTH_SHORT).show()
            startActivity(Intent(this , MainActivity::class.java))

        }

    }












}
