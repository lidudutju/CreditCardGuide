package lidu.me.creditcardguide.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import lidu.me.creditcardguide.R
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.webView

/**
 * Created by lidu on 2018/1/23.
 */
class WebViewActivity : BaseActivity() {

    private var url: String? = null
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.let {
            url = it.getStringExtra(WEBVIEW_URL_KEY)
        }

        webView.loadUrl(url)
    }

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            webView {
                webView = this
                id = R.id.activity_webview
            }
        }
    }

    companion object {
        const val WEBVIEW_URL_KEY = "url_key"
    }
}