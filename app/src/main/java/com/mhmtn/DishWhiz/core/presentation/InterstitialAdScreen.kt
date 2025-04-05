package com.mhmtn.DishWhiz.core.presentation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.mhmtn.DishWhiz.core.domain.ad.AdManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InterstitialAdScreen(activity: Activity) {
    val context = LocalContext.current
    val adManager = remember { AdManager(context) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        adManager.loadAd()

        delay(10000)
        coroutineScope.launch {
            adManager.showAd(activity)
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3 * 1000 * 60 )
            coroutineScope.launch {
                adManager.showAd(activity)
            }
        }
    }
}
