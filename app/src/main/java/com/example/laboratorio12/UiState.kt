package com.example.laboratorio12

import android.graphics.drawable.Drawable

sealed class UiState {
    class default(
        val messageDefault: String,
        val iconDefault: Drawable): UiState()
    class success(
        val messageSuccess: String,
        val iconSuccess: Drawable): UiState()
    class failure(
        val messageFailure: String,
        val iconFailure: Drawable): UiState()
    class empty(
        val messageEmpy: String,
        val iconEmpty: Drawable): UiState()
}
