package com.navigation.eazymarket.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.navigation.eazymarket.R
import kotlinx.android.synthetic.main.fragment_read_qr_code.*
import me.dm7.barcodescanner.zxing.ZXingScannerView


class ReadQrCodeFragment : Fragment(), ZXingScannerView.ResultHandler  {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_read_qr_code, container, false)

        //request permistion


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
      }
        qrCodeScanner.startCamera();

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
      //  qrCodeScanner.stopCamera()
    }

}
