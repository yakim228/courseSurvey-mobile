package com.ipnetinstitute.csc394.mobile.data.model

import java.util.*

class Survey(var title: String, var description: String, var beginMessage: String, var endMessage: String,
             var beginDate: Date, var endDate: Date, var status: Int,
             id: Int,
             createDate: Date,
             modDate: Date,
             modBy: Int
) : BaseModel(id, createDate, modDate, modBy) {
}