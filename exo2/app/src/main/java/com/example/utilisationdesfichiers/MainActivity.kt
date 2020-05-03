package com.example.utilisationdesfichiers

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_ajouter_intervention.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.io.*

class MainActivity : AppCompatActivity() {

    private lateinit var add_intervention : Button
    lateinit var recyclerView: RecyclerView
    lateinit var rechercherParDateBtn : Button
    public var listIntervention = arrayListOf<Intervention>()
    var adapter = InterventionAdapter(listIntervention , this , adapter = null)
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add_intervention = findViewById<Button>(R.id.add_intervention)
        add_intervention.setOnClickListener{
            startActivity(Intent(this , AjouterIntervention::class.java))
        }



        listIntervention = getData("file" , "",this)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter






        val datePicker = DatePicker(this)

        val button = Button(this)
        button.background.setTint(Color.parseColor("#2196F3"))
        button.setTextColor(Color.parseColor("#FFFFFF"))
        button.text = "valider"
        button.gravity = Gravity.CENTER
        var datePickerValue = ""
        button.setOnClickListener {
            datePickerValue = "${datePicker.dayOfMonth }/${datePicker.month+1}/${datePicker.year}"
            date_linear_layout.visibility = View.GONE
            rechercherParDateBtn.text = datePickerValue

            listIntervention = getData("file" , datePickerValue,this)


        }

        date_linear_layout?.addView(datePicker)
        date_linear_layout?.addView(button)

        rechercherParDateBtn = findViewById<Button>(R.id.rechercher_par_date_btn)
        rechercherParDateBtn.setOnClickListener {

            date_linear_layout.visibility = View.VISIBLE
            annuler.text = "annuler"
        }
        annuler.setOnClickListener{
            annuler.text = ""
            rechercherParDateBtn.text = "Rechercher par date"
            listIntervention = getData("file" , "" , this)
        }



    }




    fun getData(fileName : String , datePickerValue:String,context: Context) : ArrayList<Intervention>{
        try {
            var json = JSONArray(readFile(fileName , context))
            var i = json.length() - 1
            listIntervention.clear()

            while (i >= 0){

                val jsonIntervention =json.getJSONObject(i)
                if ( (datePickerValue == "") || (jsonIntervention["date"] == datePickerValue ))
                {
                    println("YYYYYYYYYYYYYYYYYYYYYYYYYYY")
                    listIntervention.add(
                        Intervention(
                            jsonIntervention["numero"].toString(),
                            jsonIntervention["date"].toString(),
                            jsonIntervention["nomPlombier"].toString(),
                            jsonIntervention["typeIntervention"].toString()
                        )
                    )
                }

                i--
            }
            adapter.notifyDataSetChanged()
            return listIntervention
        }catch (e :NullPointerException)
        {
            e.printStackTrace()
            return arrayListOf<Intervention>()
        }


    }


    override fun onResume() {
        super.onResume()

        listIntervention = getData("file" , "" , this)

    }

    public fun deleteItem(position : Int , listIntervention : ArrayList<Intervention>){

        this.listIntervention.minus(listIntervention[position])
        adapter.notifyDataSetChanged()
    }













}
