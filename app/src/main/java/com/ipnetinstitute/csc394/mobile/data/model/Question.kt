package com.ipnetinstitute.csc394.mobile.data.model

import java.util.*

class Question(var title: String, id: Int, createDate: Date, modDate: Date, modBy: Int) :
    BaseModel(id, createDate, modDate, modBy) {
}