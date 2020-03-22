package com.navigation.eazymarket.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

class DialogCustom(val context: Context) {

    companion object{
        fun getInstance(context: Context): DialogCustom {
            return DialogCustom(context)
        }
    }


    public fun showDialogGeneric(
        title: String,
        message: String,
        namePositiveButton: String,
        nameNegativeButton: String,
        function: (code: Int) -> Boolean
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
            .setPositiveButton(namePositiveButton) { _, _ ->
                function(1)
            }
            .setNegativeButton(nameNegativeButton) { _, _ ->
                function(2)
            }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}

