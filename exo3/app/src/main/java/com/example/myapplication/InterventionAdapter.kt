package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InterventionAdapter(val activity: MainActivity, var clickListner: OnIntervListener) : RecyclerView.Adapter<InterventionAdapter.TachViewHolder>() {


    class TachViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val IntervNmr = v.findViewById<TextView>(com.example.myapplication.R.id.intervNmrView)
        val IntervDate = v.findViewById<TextView>(com.example.myapplication.R.id.intervDateView)
        val IntervType = v.findViewById<TextView>(com.example.myapplication.R.id.IntervTypeView)
        val NamePlo =v.findViewById<TextView>(com.example.myapplication.R.id.IntervNomView)
        val tacheLayout = v.findViewById<RelativeLayout>(com.example.myapplication.R.id.tacheLayoutView)
        fun initialize(item:Intervention, action: OnIntervListener){
            IntervNmr.text = item.num
            IntervDate.text = item.date
            IntervType.text = item.Type
            NamePlo.text = item.nom
            tacheLayout.setOnClickListener {
                action.OnItemClick(item,adapterPosition)
            }

        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TachViewHolder {
        return TachViewHolder(
            LayoutInflater.from(activity).inflate(com.example.myapplication.R.layout.interv_view, parent, false
            ))
    }

    override fun getItemCount(): Int {
        return activity.interventionList!!.size
    }

    override fun onBindViewHolder(holder: TachViewHolder, position: Int) {

        holder.initialize(activity.interventionList!!.get(position),clickListner)



    }
    public interface OnIntervListener{
        fun OnItemClick(item:Intervention, position: Int)
    }


}


