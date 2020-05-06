package com.ipnetinstitute.csc394.mobile.data.model

import java.util.*

class Term(var code:String, var name:String, id: Long, createDate: Date, modDate: Date, modBy: Long) :
    BaseModel(id, createDate, modDate, modBy) {
}