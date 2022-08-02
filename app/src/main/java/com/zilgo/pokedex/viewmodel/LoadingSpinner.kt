package com.zilgo.pokedex.viewmodel

import android.app.Activity
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.zilgo.pokedex.R


class LoadingSpinner (private val activity: AppCompatActivity){
    private lateinit var isdialog: AlertDialog

    fun startLoading() {
        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_itens, null)

        val builder = AlertDialog.Builder(activity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isdialog = builder.create()
        isdialog.show()
    }

    fun dismiss() {
        isdialog.dismiss()
    }
}