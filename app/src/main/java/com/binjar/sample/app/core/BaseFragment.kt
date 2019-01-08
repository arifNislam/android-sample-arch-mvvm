package com.binjar.sample.app.core

import android.content.Context
import androidx.fragment.app.Fragment


abstract class BaseFragment : Fragment() {

    var fragmentHost: FragmentHost? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentHost) {
            fragmentHost = context
        }
    }
}
