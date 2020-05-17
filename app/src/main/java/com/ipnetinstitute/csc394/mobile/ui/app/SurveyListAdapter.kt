package com.ipnetinstitute.csc394.mobile.ui.app


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ipnetinstitute.csc394.mobile.R
import com.ipnetinstitute.csc394.mobile.data.model.Survey

class SurveyListAdapter(var surveyList: List<Survey>):
    RecyclerView.Adapter<SurveyListAdapter.ViewHolder>() {
    class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        val titleView = v.findViewById<TextView>(R.id.surveyTitle)
        val descriptionView = v.findViewById<TextView>(R.id.surveyDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.survey_item,parent,false)
        return ViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return surveyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val surveyItem = surveyList[position]
        holder.titleView.text = surveyItem.title
        holder.descriptionView.text = surveyItem.description
    }

}

//val context = parent.context
//val inflater = LayoutInflater.from(context)
//val view = inflater.inflate(R.layout.survey_item, parent, false)
