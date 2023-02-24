package com.ns.theendcompose

import com.ns.theendcompose.utils.NetworkStatusTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkStatusTracker: NetworkStatusTracker
) {
}