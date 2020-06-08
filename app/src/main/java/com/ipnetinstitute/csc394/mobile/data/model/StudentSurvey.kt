package com.ipnetinstitute.csc394.mobile.data.model

import java.util.*

class StudentSurvey (var id_survey: Int, var id_student: Int, var id_question: Int, var comments: String, var is_na: Int, var rating: Int, id: Int, createDate: Date, modDate: Date, modBy: Int) :
    BaseModel(id, createDate, modDate, modBy){
}