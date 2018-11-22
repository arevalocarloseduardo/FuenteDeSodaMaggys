package com.yadaapps.caear.pedidosmaggys.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yadaapps.caear.pedidosmaggys.R

class PedirFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val v =inflater.inflate(R.layout.fragment_pedir, container, false)
        return v
    }


}
