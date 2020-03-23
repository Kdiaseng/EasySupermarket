package com.navigation.eazymarket.utils

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.navigation.eazymarket.R
import com.navigation.eazymarket.domain.SupermarketProductJoin
import kotlinx.android.synthetic.main.dialog_with_input.view.*
import kotlinx.android.synthetic.main.dialog_with_input_and_swtch.view.*

class DialogCustom(val context: Context) {

    companion object {
        fun getInstance(context: Context): DialogCustom {
            return DialogCustom(context)
        }
    }


    fun showDialogGeneric(
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
        function: (value: String, supermarketProductJoin: SupermarketProductJoin) -> Boolean,
        supermarketProductJoin: SupermarketProductJoin
    ) {


        val builder = AlertDialog.Builder(context)
        val view = inflater.inflate(R.layout.dialog_with_input, null)
        view.textview_name_product_diolog.text = message
        view.textview_title.text = title
        view.textInputValueDialog.setText(value.toString())

        builder.setView(view)
        builder.setPositiveButton(namePositiveButton) { dialogInterface, _ ->
            dialogInterface.dismiss()
            function(view.textInputValueDialog.text.toString(), supermarketProductJoin)
        }

        builder.setNegativeButton(nameNegativeButton) { dialogInterface, _ ->
            dialogInterface.cancel()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    fun showDialogWithInputAndSwith(
        inflater: LayoutInflater,
        title: String,
        message: String,
        value: Double,
        namePositiveButton: String,
        nameNegativeButton: String,
        saveInSupermarket: (SupermarketProductJoin) -> Boolean,
        supermarketProductJoin: SupermarketProductJoin
    ) {

        val builder = AlertDialog.Builder(context)
        val view = inflater.inflate(R.layout.dialog_with_input_and_swtch, null)
        view.textview_name_input_switch.text = message
        view.textview_title_input_switch.text = title
        view.textInputValueDialogInputSwitch.setText(value.toString())

        view.switch_add_car_dialog_input_switch.setOnCheckedChangeListener { _, isChecked ->
            view.switch_add_car_dialog_input_switch.text =
                if (isChecked) "Item adicionado" else "Item não adicionado"
        }
        builder.setView(view)
        builder.setPositiveButton(namePositiveButton) { dialogInterface, _ ->
            dialogInterface.dismiss()
            if (view.switch_add_car_dialog_input_switch.isChecked)
                supermarketProductJoin.quantity = 1

            supermarketProductJoin.valueProdut = if (view.textInputValueDialogInputSwitch.text.toString()
                    .isEmpty()
            ) 0.0 else view.textInputValueDialogInputSwitch.text.toString().toDouble()

            Log.e("MERDA", "SIM")
          //  saveInSupermarket(supermarketProductJoin)
        }

        builder.setNegativeButton(nameNegativeButton) { dialogInterface, _ ->
            dialogInterface.cancel()
            Log.e("MERDA", "CANCELAR")
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()

    }
}

