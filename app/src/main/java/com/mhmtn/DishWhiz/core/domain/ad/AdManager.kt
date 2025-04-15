package com.mhmtn.DishWhiz.core.domain.ad

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.mhmtn.DishWhiz.core.domain.util.Constants.AD_UNIT_ID

class AdManager(private val context: Context) {
    private var interstitialAd: InterstitialAd? = null

    fun loadAd(onAdLoaded: (() -> Unit)? = null) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            AD_UNIT_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    onAdLoaded?.invoke()
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                    Log.e("AdManager", "Ad failed to load: ${error.message}")
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