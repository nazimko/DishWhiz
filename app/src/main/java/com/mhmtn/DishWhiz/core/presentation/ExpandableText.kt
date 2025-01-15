package com.mhmtn.DishWhiz.core.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    text: String,
    collapsedMaxLines: Int = 3,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = text,
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLines,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = if (isExpanded) "Show More" else "Show Less",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { isExpanded = !isExpanded },
            style = MaterialTheme.typography.bodySmall
        )
    }
}
