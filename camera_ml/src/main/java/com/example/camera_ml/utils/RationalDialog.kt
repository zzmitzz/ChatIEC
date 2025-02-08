package com.example.camera_ml.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity


class RationalDialog {
    final val TAG = "RationalDialog"

    interface DialogListener {
        // equivalent to right click
        fun onPositiveButtonClick()
        // equivalent to left click
        fun onNegativeButtonClick()
    }
    companion object {

        private var dialog: AlertDialog? = null
        fun Builder(activity: Activity,
                    title: String,
                    message: String,
                    action: DialogListener): AlertDialog? {
            dialog = AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes") { _, _ ->
                    action.onPositiveButtonClick()
                }
                .setNegativeButton("No") { _, _ ->
                    action.onNegativeButtonClick()
                }
                .setCancelable(true)
                .create()
            return dialog
        }
    }
    fun showDialog(){
        dialog?.show() ?: throw (Exception("dialog is null"))
    }
    fun hideDialog(){
        if(dialog?.isShowing == true) dialog?.dismiss()
    }
}