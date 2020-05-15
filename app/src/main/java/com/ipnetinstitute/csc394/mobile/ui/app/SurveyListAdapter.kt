package com.ipnetinstitute.csc394.mobile.ui.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ipnetinstitute.csc394.mobile.R
import com.ipnetinstitute.csc394.mobile.data.model.Survey

class SurveyListAdapter(var surveyList: List<Survey>) :
    RecyclerView.Adapter<SurveyListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurveyListViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.survey_item, parent, false)
        return SurveyListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return surveyList.size
    }

    override fun onBindViewHolder(holder: SurveyListViewHolder, position: Int) {
        val currentSurvey = surveyList[position]
        holder.updateSurveyValue(currentSurvey.title, currentSurvey.description)
    }
}