package com.ipnetinstitute.csc394.mobile.ui.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ipnetinstitute.csc394.mobile.R

class HomeFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home_layout,container,false)

        return rootView
    }

    companion object{
        fun newInstance(): HomeFragment = HomeFragment()
    }
}