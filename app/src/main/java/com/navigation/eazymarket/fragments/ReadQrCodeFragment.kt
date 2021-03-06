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
import com.navigation.eazymarket.domain.SupermarketProductJoin
import com.navigation.eazymarket.model.RegisterProductParam
import com.navigation.eazymarket.utils.DialogCustom
import kotlinx.android.synthetic.main.fragment_read_qr_code.*
import me.dm7.barcodescanner.zxing.ZXingScannerView


class ReadQrCodeFragment : Fragment(), ZXingScannerView.ResultHandler {

    private var supermarketId: Long = 0
    private var codeRead: String? = null

    companion object {
         private const val ACTION_SHOW_REGISTER_PRODUCT_SCREEN_SHOW = 1
        private const val START_CAMERA = 2

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
            codeRead = rawResult.text.toString()
            val product = getProductByCode(rawResult.text.toString())
            if (product != null) {
                val supermarketProductJoin =
                    getProductInCurrentSupermarket(product.id, supermarketId)
                if (supermarketProductJoin != null) {
                    checkAndConfirmProductValue(product, supermarketProductJoin)
                    openCamera()
                } else {
                    confirmationAddProductInSupermarket(product)
                    openCamera()
                }
            } else {
                questionRegisterCode()
            }
        }
    }

    private fun openCamera() {
        qrCodeScanner.startCamera()
        qrCodeScanner.setResultHandler(this)
    }

    private fun confirmationAddProductInSupermarket(product: Product) {
        DialogCustom.getInstance(activity!!).showDialogWithInputAndSwith(
            requireActivity().layoutInflater,
            "Adicionar produto no supermercado?",
            product.name,
            0.0,
            "SIM",
            "NÃO",
            ::saveProductInSupermarket,
            SupermarketProductJoin(supermarketId, product.id, 0.0)
        )
    }

    private fun questionRegisterCode() {
        DialogCustom.getInstance(activity!!).showDialogGeneric(
            "Código não cadastrado",
            "Deseja cadastrar o código lido?",
            "SIM",
            "NÃO",
            ::actionDialog,
            ACTION_SHOW_REGISTER_PRODUCT_SCREEN_SHOW,
            START_CAMERA
        )
    }

    private fun checkAndConfirmProductValue(
        product: Product,
        supermarketProductJoin: SupermarketProductJoin
    ) {
        val inflater = requireActivity().layoutInflater
        DialogCustom.getInstance(activity!!).showDialogWithInput(
            inflater,
            "Deseja adicionar no carrinho?",
            product.name,
            supermarketProductJoin.valueProdut,
            "SIM",
            "NÃO",
            ::addProductInCar,
            supermarketProductJoin
        )
    }

    private fun addProductInCar(
        value: String,
        supermarketProductJoin: SupermarketProductJoin
    ): Boolean {
        supermarketProductJoin.valueProdut = value.toDouble()
        supermarketProductJoin.quantity = 1
        AppDatabase(activity!!).supermarketProductJoinDao().update(supermarketProductJoin)
        setUsingSupermarket(supermarketId, true)
        Toast.makeText(activity, "Item adicionado com sucesso!", Toast.LENGTH_SHORT).show()
        return true
    }

    private fun showRegisterProductScreen() {
        val action = ReadQrCodeFragmentDirections.actionReadQrCodeFragmentToRegisterProductFragment(RegisterProductParam(supermarketId, codeRead!! ))
         Navigation.findNavController(this.requireView()).navigate(action)
    }

    private fun saveProductInSupermarket(supermarketProductJoin: SupermarketProductJoin) {
        AppDatabase(activity!!).supermarketProductJoinDao().insert(supermarketProductJoin)
        Toast.makeText(
            activity,
            "Produto adicionado ao supermercado com sucesso!",
            Toast.LENGTH_SHORT
        ).show()

    }


    private fun actionDialog(code: Int) {
        when (code) {
            ACTION_SHOW_REGISTER_PRODUCT_SCREEN_SHOW -> showRegisterProductScreen()
            START_CAMERA -> qrCodeScanner.startCamera()
            else -> {
                Toast.makeText(activity, "Não existe operação para esse item", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    private fun getProductByCode(code: String): Product? {
        return AppDatabase(activity!!).productDao().getProductForCode(code)
    }

    private fun getProductInCurrentSupermarket(
        IdProduct: Long,
        IdSupermarket: Long
    ): SupermarketProductJoin? {
        return AppDatabase(activity!!).supermarketProductJoinDao()
            .verifyProductInSupermarket(IdSupermarket, IdProduct)
    }


    override fun onResume() {
        super.onResume()
        if (qrCodeScanner == null) {
            qrCodeScanner.startCamera()
            qrCodeScanner.setResultHandler(this)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (qrCodeScanner != null) {
            qrCodeScanner.stopCamera()
            qrCodeScanner.setResultHandler(this)
        }
    }

    private fun setUsingSupermarket(id: Long, isUsed: Boolean){
        AppDatabase(activity!!).supermarketDao().setUsingSupermarket(id, isUsed)
    }

}
