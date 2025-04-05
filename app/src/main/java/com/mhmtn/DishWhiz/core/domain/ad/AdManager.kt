package com.mhmtn.DishWhiz.core.domain.ad

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class AdManager(private val context: Context) {
    private var interstitialAd: InterstitialAd? = null

    fun loadAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            "ca-app-pub-3239252626734491/2490946284",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                }
            })
    }

    fun showAd(activity: Activity) {
        interstitialAd?.let { ad ->
            ad.show(activity)
            loadAd()
        }
    }
}
