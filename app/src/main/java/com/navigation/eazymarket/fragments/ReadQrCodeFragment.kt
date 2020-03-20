package com.navigation.eazymarket.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import kotlinx.android.synthetic.main.fragment_read_qr_code.*
import me.dm7.barcodescanner.zxing.ZXingScannerView


class ReadQrCodeFragment : Fragment(), ZXingScannerView.ResultHandler {

    private var supermarketId: Long = 0

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
            if (hasCodeInProduct("5522")) {
                Toast.makeText(activity, "Adcionado com sucesso", Toast.LENGTH_SHORT).show()
            } else {
                val action = ReadQrCodeFragmentDirections.actionReadQrCodeFragmentToRegisterProductFragment()
                action.supermarketId = this.supermarketId
                Navigation.findNavController(this.requireView()).navigate(action)
            }
        }        //  qrCodeScanner.startCamera()

    }

    private fun hasCodeInProduct(code: String): Boolean {
        val result = findProductByIdSupermarketAndCode(this.supermarketId,code)
        if (result.isNotEmpty()){
            return true
        }
        return false
    }

    private fun findProductByCode(code: String): Product? {
        return (AppDatabase(activity!!).productDao().getProductForCode(code))
    }

    fun findProductByIdSupermarketAndCode(supermarketId: Long, code: String): Array<Product>{
       return (AppDatabase(activity!!).supermarketProductJoinDao().getProductForIdSupermarketFromCode(supermarketId, code))
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
