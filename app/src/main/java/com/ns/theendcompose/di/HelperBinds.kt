package com.ns.theendcompose.di

import com.ns.theendcompose.utils.TextRecognitionHelper
import com.ns.theendcompose.utils.TextRecognitionHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface HelperBinds {
    @Binds
    fun provideTextRecognitionHelper(impl: TextRecognitionHelperImpl): TextRecognitionHelper
}