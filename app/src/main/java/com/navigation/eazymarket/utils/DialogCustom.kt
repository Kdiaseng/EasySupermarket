package com.navigation.eazymarket.utils

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.navigation.eazymarket.R
import com.navigation.eazymarket.domain.SupermarketProductJoin
import kotlinx.android.synthetic.main.dialog_with_input.view.*

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
        function: (code: Int) -> Boolean,
        codeFunctionPositive: Int,
        CodeFunctionNegative: Int
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
            .setPositiveButton(namePositiveButton) { _, _ ->
                function(codeFunctionPositive)
            }
            .setNegativeButton(nameNegativeButton) { _, _ ->
                function(CodeFunctionNegative)
            }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun showDialogWithInput(
        inflater: LayoutInflater,
        title: String,
        message: String,
        value: Double,
        namePositiveButton: String,
        nameNegativeButton: String,
        function: (value: String, supermarketProductJoin:SupermarketProductJoin ) -> Boolean,
        supermarketProductJoin: SupermarketProductJoin
       ){


        val builder = AlertDialog.Builder(context)
        val view = inflater.inflate(R.layout.dialog_with_input, null)
        view.textview_name_product_diolog.text = message
        view.textview_title.text = title
        view.textInputValueDialog.setText(value.toString())

        builder.setView(view)
        builder.setPositiveButton(namePositiveButton){ dialogInterface, _ ->
            dialogInterface.dismiss()
            function(view.textInputValueDialog.text.toString(), supermarketProductJoin)
        }

        builder.setNegativeButton(nameNegativeButton){ dialogInterface, _ ->
            dialogInterface.cancel()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()

    }
}

