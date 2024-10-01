package com.ece.e_cart

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ece.e_cart.ui.theme.EcartTheme

class MainActivity : ComponentActivity() {
    private lateinit var webView: WebView  // Declare WebView as a class property

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcartTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WebViewScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    // Override back pressed to handle WebView back navigation
    override fun onBackPressed() {
        if (::webView.isInitialized && webView.canGoBack()) {
            webView.goBack()  // Go back in WebView history
        } else {
            super.onBackPressed()  // Exit the app if there's no history
        }
    }

    @Composable
    fun WebViewScreen(modifier: Modifier = Modifier) {
        // Create an AndroidView for WebView
        androidx.compose.ui.viewinterop.AndroidView(
            modifier = modifier,
            factory = { context ->
                WebView(context).apply {
                    // Enable JavaScript
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true

                    setBackgroundColor(android.graphics.Color.TRANSPARENT)
                    // Set WebViewClient to handle loading within the app
                    webViewClient = WebViewClient()

                    // Set WebChromeClient to enable JavaScript alerts and other features
                    webChromeClient = WebChromeClient()

                    // Load your desired URL
                    loadUrl("https://www.geeksforgeeks.org/") // Replace with your URL
                }.also { webView = it }  // Assign the WebView to the class property
            },
            update = { webView ->
                webView.loadUrl("https://www.geeksforgeeks.org/") // Update URL if needed
            }
        )
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        EcartTheme {
            Greeting("Android")
        }
    }
}
