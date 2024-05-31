package com.example.directusapp.ui

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser

@Composable
fun MarkdownView(markdownText: String) {
    val htmlContent = markdownToHtml(markdownText)

    AndroidView(factory = { context ->
        WebView(context).apply {
            webViewClient = WebViewClient()
            loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)
        }
    }, update = {
        it.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)
    })
}

fun markdownToHtml(markdownText: String): String {
    val flavour = GFMFlavourDescriptor()
    val parser = MarkdownParser(flavour)
    val parsedTree = parser.buildMarkdownTreeFromString(markdownText)
    val htmlGenerator = HtmlGenerator(markdownText, parsedTree, flavour)
    return htmlGenerator.generateHtml()
}