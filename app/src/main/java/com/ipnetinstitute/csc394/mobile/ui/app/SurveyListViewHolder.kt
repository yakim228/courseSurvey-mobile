package com.ipnetinstitute.csc394.mobile.ui.app

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.ipnetinstitute.csc394.mobile.R

class SurveyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.surveyTitle)
    lateinit var surveyTitleView: TextView

    @BindView(R.id.surveyDescription)
    lateinit var surveyDescriptionView: TextView

    fun updateSurveyValue(title: String, description: String) {
        surveyTitleView.text = title
        surveyDescriptionView.text = description
    }
}