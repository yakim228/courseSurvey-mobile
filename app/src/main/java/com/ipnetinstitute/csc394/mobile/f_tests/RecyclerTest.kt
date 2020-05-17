package com.ipnetinstitute.csc394.mobile.f_tests

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ipnetinstitute.csc394.mobile.R
import kotlinx.android.synthetic.main.activity_recycler_test.*

class RecyclerTest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_test)

        val textList = ArrayList<String>()
        textList.add("First")
        textList.add("Second")
        textList.add("Third")
        textList.add("Fourth")

        textListView.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerTestAdapter(textList)
        textListView.adapter = adapter
    }
}
