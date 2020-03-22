package com.navigation.eazymarket.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.navigation.eazymarket.R
import com.navigation.eazymarket.database.AppDatabase
import com.navigation.eazymarket.domain.Product
import com.navigation.eazymarket.utils.DialogCustom
import kotlinx.android.synthetic.main.fragment_read_qr_code.*
import me.dm7.barcodescanner.zxing.ZXingScannerView


class ReadQrCodeFragment : Fragment(), ZXingScannerView.ResultHandler {

    private var supermarketId: Long = 0

    companion object {
        private val NOT_FOUND_PRODUCT: Long = -1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_read_qr_code, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            this.supermarketId = ReadQrCodeFragmentArgs.fromBundle(it).supermarketId
        }

        Dexter.withActivity(activity)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    qrCodeScanner.setResultHandler(this@ReadQrCodeFragment)
                    qrCodeScanner.startCamera()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    Toast.makeText(
                        context,
                        "You must accept permission",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest,
                    token: PermissionToken
                ) {
                }
            }).check()
    }

    override fun handleResult(rawResult: Result?) {
        rawResult?.let {
            txt_result.text = rawResult.text
            val product = getProductByCode("1234")
            if (product != null) {
                if (hasProductInCurrentSupermarket(product.id, supermarketId)) {
                    //showAlertDialog()
                } else {
                    //showAletDialogToInputValueProduct()
                    //   saveProductInSupermarket(product.id, supermarketId)
                    //addProductInCar()
                }
            } else {
                DialogCustom.getInstance(activity!!).showDialogGeneric(
                    "Código não cadastrado",
                    "Deseja cadastrar o código lido?",
                    "SIM",
                    "NÃO",
                    ::actionDialog
                )
                //showDialogAskWantSaveCode()
            }
        }
    }

    private fun showRegisterProductScreen() {
        val action =
            ReadQrCodeFragmentDirections.actionReadQrCodeFragmentToRegisterProductFragment()
        action.supermarketId = this.supermarketId
        Navigation.findNavController(this.requireView()).navigate(action)
    }

    /* private fun saveProductInSupermarket(idProduct: Long, idSupermarket: Long){
         AppDatabase(activity!!).supermarketProductJoinDao().insert(SupermarketProductJoin(idSupermarket,idProduct,))
     }*/


    private fun actionDialog(code: Int): Boolean {
        if (code == 1) {
            showRegisterProductScreen()
        } else {
            qrCodeScanner.startCamera()
        }
        return true
    }


    private fun showDialogAskWantSaveCode() {
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle("Código não cadastrado")
        builder.setMessage("Deseja cadastrar o código lido?")
            .setPositiveButton("SIM") { _, _ ->
                showRegisterProductScreen()
            }
            .setNegativeButton("CANCELAR") { _, _ ->
                qrCodeScanner.startCamera()
            }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun getProductByCode(code: String): Product? {
        return AppDatabase(activity!!).productDao().getProductForCode(code)
    }

    private fun hasProductInCurrentSupermarket(IdProduct: Long, IdSupermarket: Long): Boolean {
        val result = AppDatabase(activity!!).supermarketProductJoinDao()
            .verifyProductInSupermarket(IdSupermarket, IdProduct)

        if (result != null) {
            return true
        }
        return false
    }


    override fun onResume() {
        super.onResume()
        qrCodeScanner.startCamera()
    }

    override fun onStop() {
        super.onStop()
        qrCodeScanner.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        qrCodeScanner.startCamera()
    }


}
