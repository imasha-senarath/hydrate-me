package com.imasha.hydrateme.network

import android.content.Context
import com.imasha.hydrateme.R
import okhttp3.OkHttpClient
import java.lang.System.load
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class OkHttpClientProvider {

    /*fun getPinnedOkHttpClient(context: Context): OkHttpClient {
        // Load the certificate from res/raw
        val certInputStream = context.resources.openRawResource(R.raw.cert)

        // Generate Certificate
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val certificate = certificateFactory.generateCertificate(certInputStream) as X509Certificate
        certInputStream.close()

        // Create a KeyStore and add the certificate
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
            load(null, null)
            setCertificateEntry("ca", certificate)
        }

        // Create a TrustManagerFactory
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)

        // Get the TrustManager
        val trustManagers = trustManagerFactory.trustManagers
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustManagers, null)

        // Use the custom TrustManager in OkHttpClient
        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustManagers[0] as X509TrustManager)
            .build()
    }*/
}