package com.ns.theendcompose.domain.usecase

import com.ns.theendcompose.data.repository.config.ConfigRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCameraAvailableUseCaseImpl @Inject constructor(
    private val configRepository: ConfigRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return configRepository.getCameraAvailable()
    }
}