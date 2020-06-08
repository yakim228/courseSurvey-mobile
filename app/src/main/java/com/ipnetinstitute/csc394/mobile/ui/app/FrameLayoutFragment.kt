package com.ipnetinstitute.csc394.mobile.ui.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ipnetinstitute.csc394.mobile.R

class FrameLayoutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_frame, container, false)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        val pendingSurveyList = PendingSurveyList()
        fragmentTransaction.replace(R.id.fragment_container, pendingSurveyList)
        fragmentTransaction.commit()
    }
}