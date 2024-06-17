package com.example.mvvm_kotlin_assignment.utils

import android.app.Activity
import android.app.AlertDialog
import com.example.mvvm_kotlin_assignment.R

class LoadingUtils(val activity: Activity){
    lateinit var alertDialog: AlertDialog

    fun showLoading(){
        var dialogview = activity.layoutInflater.inflate(R.layout.loading_dialog,null)
        var builder = AlertDialog.Builder(activity)
        builder.setView(dialogview)
        builder.setCancelable(false)
        alertDialog = builder.create()
        alertDialog.show()
    }

    fun dismiss(){
        alertDialog.dismiss()
    }
}