package com.mhmtn.DishWhiz.core.presentation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.mhmtn.DishWhiz.core.domain.ad.AdManager
import com.mhmtn.DishWhiz.core.domain.ad.ScreenVisitManager

@Composable
fun InterstitialAdHandler(
    activity: Activity,
) {
    val context = LocalContext.current
    val adManager = remember { AdManager(context) }

    val screenVisitManager = remember { ScreenVisitManager(context) }

    LaunchedEffect(Unit) {
        val count = screenVisitManager.incrementVisitCount()
        println("Ekran Ziyaret Sayısı: $count")

        if (count == 1 || count % 3 == 1) {
            adManager.loadAd {
                adManager.showAd(activity)
                println("Reklam Gösterildi (Ziyaret: $count)")
            }
        }
    }
}
