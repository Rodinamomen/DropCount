package com.example.stayhydratedapp.tracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stayhydratedapp.R
import com.example.stayhydratedapp.database.Record

class RecordsAdapter(private val data: List<Record>): RecyclerView.Adapter<RecordsAdapter.MyHolder>() {
    class MyHolder(val row: View): RecyclerView.ViewHolder(row){
        var recordDate = row.findViewById<TextView>(R.id.tv_record_time)
        var recordIntakeAmount = row.findViewById<TextView>(R.id.tv_record_intake_amount)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordsAdapter.MyHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.record_item,parent, false)
        return MyHolder(row)
    }

    override fun onBindViewHolder(holder: RecordsAdapter.MyHolder, position: Int) {
         holder.recordDate.text = data[position].recordDate.toString()
        holder.recordIntakeAmount.text = "${data[position].recordIntakeAmount}Ml"
    }

    override fun getItemCount(): Int {
       return data.size
    }
}