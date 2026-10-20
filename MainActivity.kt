package com.agona.tropmarket

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.content.Intent
import android.widget.FrameLayout
import android.widget.ProgressBar

class MainActivity : Activity() {
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var root: FrameLayout
    private var showingOfflinePage = false

    private val startUrl = "https://milesbrownxx-creator.github.io/-trop-market-uganda/"
    private val siteHost = "milesbrownxx-creator.github.io"
    private val sitePathPrefix = "/-trop-market-uganda"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(16, 24, 39)
        window.navigationBarColor = Color.rgb(16, 24, 39)

        root = FrameLayout(this)
        root.setBackgroundColor(Color.rgb(245, 247, 251))
        webView = WebView(this)
        progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal)
        progressBar.max = 100
        progressBar.progress = 0
        progressBar.progressTintList = android.content.res.ColorStateList.valueOf(Color.rgb(22, 184, 166))
        progressBar.indeterminateTintList = android.content.res.ColorStateList.valueOf(Color.rgb(22, 184, 166))

        root.addView(webView, FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        ))
        root.addView(progressBar, FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            dp(3),
            Gravity.TOP
        ))
        setContentView(root)

        val settings: WebSettings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.loadsImagesAutomatically = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.setSupportMultipleWindows(false)
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.mediaPlaybackRequiresUserGesture = true
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progressBar.progress = newProgress
                progressBar.visibility = if (newProgress in 1..99) View.VISIBLE else View.GONE
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
            }
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val uri = request?.url ?: return false
                return routeUrl(uri)
            }

            @Suppress("DEPRECATION")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url.isNullOrBlank()) return false
                return routeUrl(Uri.parse(url))
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                if (url?.startsWith(startUrl) == true) showingOfflinePage = false
                progressBar.visibility = View.VISIBLE
                progressBar.progress = 5
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.progress = 100
                progressBar.visibility = View.GONE
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                if (request?.isForMainFrame == true && !showingOfflinePage) {
                    showOfflinePage()
                }
            }
        }

        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState)
        } else {
            webView.loadUrl(startUrl)
        }
    }

    private fun routeUrl(uri: Uri): Boolean {
        val scheme = uri.scheme?.lowercase() ?: return false
        if (scheme != "https" && scheme != "http") {
            return try {
                startActivity(Intent(Intent.ACTION_VIEW, uri))
                true
            } catch (_: Exception) {
                true
            }
        }

        val isOurSite = uri.host.equals(siteHost, ignoreCase = true) &&
            (uri.path.orEmpty().startsWith(sitePathPrefix))
        if (isOurSite) return false

        return try {
            startActivity(Intent(Intent.ACTION_VIEW, uri))
            true
        } catch (_: Exception) {
            true
        }
    }

    private fun showOfflinePage() {
        showingOfflinePage = true
        val page = """
            <!doctype html><html><head><meta name='viewport' content='width=device-width, initial-scale=1'>
            <style>body{margin:0;background:#f5f7fb;color:#172033;font-family:Arial,sans-serif;display:flex;min-height:100vh;align-items:center;justify-content:center;text-align:center;padding:24px;box-sizing:border-box}.card{max-width:420px;background:white;border-radius:22px;padding:28px;box-shadow:0 10px 30px #10182712}h1{font-size:25px;margin:0 0 10px}p{color:#647084;line-height:1.6}button{border:0;border-radius:12px;padding:14px 20px;font-size:16px;font-weight:bold;background:#16b8a6;color:#062d32}</style></head>
            <body><div class='card'><h1>TROP Market Uganda</h1><p>We couldn't load the shop. Check your internet connection and try again.</p><button onclick="location.href='$startUrl'">Try again</button><p style='font-size:12px'>Developer: Agona Peter</p></div></body></html>
        """.trimIndent()
        webView.loadDataWithBaseURL(startUrl, page, "text/html", "UTF-8", null)
        progressBar.visibility = View.GONE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        webView.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    @Deprecated("Use device back navigation")
    override fun onBackPressed() {
        if (webView.canGoBack() && !showingOfflinePage) {
            webView.goBack()
        } else {
            AlertDialog.Builder(this)
                .setTitle("Exit TROP Market?")
                .setMessage("Do you want to close TROP Market Uganda?")
                .setNegativeButton("Stay", null)
                .setPositiveButton("Exit") { _, _ -> finish() }
                .show()
        }
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()

    override fun onDestroy() {
        if (::webView.isInitialized) {
            webView.stopLoading()
            webView.destroy()
        }
        super.onDestroy()
    }
}
