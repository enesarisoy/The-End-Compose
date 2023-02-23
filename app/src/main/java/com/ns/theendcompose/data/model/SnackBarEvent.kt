package com.ns.theendcompose.data.model

import androidx.annotation.StringRes
import com.ns.theendcompose.R

sealed class SnackBarEvent(@StringRes val messageStringRes: Int) {
    object NetworkDisconnected : SnackBarEvent(R.string.snack_bar_network_disconnected_label)
    object NetworkConnected : SnackBarEvent(R.string.snack_bar_network_connected_label)
}