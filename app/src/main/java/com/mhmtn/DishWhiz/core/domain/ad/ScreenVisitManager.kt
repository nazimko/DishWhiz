package com.mhmtn.DishWhiz.core.domain.ad

import android.content.Context
import android.content.SharedPreferences
import com.mhmtn.DishWhiz.core.domain.util.Constants.VISIT_COUNT_KEY

class ScreenVisitManager(private val context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    fun getVisitCount(): Int {
        return sharedPreferences.getInt(VISIT_COUNT_KEY, 0)
    }

    fun incrementVisitCount(): Int {
        val currentCount = getVisitCount()
        val newCount = currentCount + 1
        sharedPreferences.edit().putInt(VISIT_COUNT_KEY, newCount).apply()
        return newCount
    }

    fun resetVisitCount() {
        sharedPreferences.edit().putInt(VISIT_COUNT_KEY, 0).apply()
    }
}

