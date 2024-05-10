package com.example.verificacodiceleadtech.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.getErrorBorder(isError: Boolean) =
    this.border(
        width = if (isError) 1.dp else 0.dp,
        color = if (isError) Color.Red else Color.Transparent,
        shape = RoundedCornerShape(14.dp)
    )

fun Modifier.getFocusedBorder(isFocused: Boolean) =
    this.border(
        width = if (isFocused) 1.dp else 0.dp,
        color = if (isFocused) Color.Black else Color.Transparent,
        shape = RoundedCornerShape(14.dp)
    )