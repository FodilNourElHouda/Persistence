package com.example.myapplication

import androidx.room.*


@Dao
public interface IntervDAO {
    @Query("SELECT * FROM intervention")
    fun getIntervs(): MutableList<Intervention>

    @Query("SELECT * FROM intervention WHERE id = :code")
    fun getInterv(code: Int): List<Intervention>


    @Query("SELECT * FROM intervention WHERE  Date = :date1")
    fun getIntervss(date1  :String): MutableList<Intervention>

    @Insert
    fun ajouter(intervention : Intervention)

    @Update
    fun modifier(intervention : Intervention)

    @Delete
    fun supprimer(intervention: Intervention)
}