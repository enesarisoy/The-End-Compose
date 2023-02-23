package com.ns.theendcompose.data.model


sealed class PresentableItemState {
    object Loading : PresentableItemState()
    object Error : PresentableItemState()
    data class Result(val presentable: Presentable) : PresentableItemState()
}