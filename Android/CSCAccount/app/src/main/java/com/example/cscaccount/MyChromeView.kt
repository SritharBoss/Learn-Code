package com.example.cscaccount

import android.R
import android.content.Context
import android.os.Vibrator
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText


class MyChromeView(private val context: Context) : WebChromeClient() {

    var vibe = context.getSystemService(AppCompatActivity.VIBRATOR_SERVICE) as Vibrator

    override fun onJsAlert(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult
    ): Boolean {
        MaterialAlertDialogBuilder(context).setTitle("Alert")
            .setMessage(message)
            .setPositiveButton(R.string.ok){ _, _ ->
                result.confirm()
            }.setOnCancelListener { result.cancel();it.dismiss()}.show()
        return true
    }

    override fun onJsConfirm(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult
    ): Boolean {

        MaterialAlertDialogBuilder(context).setTitle("Alert")
            .setMessage(message)
            .setPositiveButton(R.string.ok){ _, _ ->
                result.confirm()
            }.setNegativeButton(R.string.cancel){dialog,_ ->
                result.cancel()
                dialog.dismiss()
            }.setOnCancelListener { result.cancel();it.dismiss()}.show()
        return true
    }

    override fun onJsPrompt(
        view: WebView?,
        url: String?,
        message: String?,
        defaultValue: String?,
        result: JsPromptResult
    ): Boolean {

        val textView = TextInputEditText(context)
        MaterialAlertDialogBuilder(context).setTitle("Alert")
            .setMessage(message)
            .setView(textView)
            .setPositiveButton(
                R.string.ok
            ) { _, _ -> // Inform the WebView about the user's input
                result.confirm(textView.text.toString())
            }
            .setNegativeButton(R.string.cancel) { dialog, _ -> // Inform the WebView that the user canceled
                result.cancel()
                dialog.dismiss()
            }
            .setOnCancelListener { // Inform the WebView that the user canceled (when the dialog is canceled by pressing back button)
                result.cancel();it.dismiss()
            }
            .show()
        return true
    }

}