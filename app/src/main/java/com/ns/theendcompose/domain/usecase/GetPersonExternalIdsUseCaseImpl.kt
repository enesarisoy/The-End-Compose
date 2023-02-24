package com.ns.theendcompose.domain.usecase

import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.data.model.ExternalIds
import com.ns.theendcompose.data.remote.api.ApiResponse
import com.ns.theendcompose.data.remote.api.awaitApiResponse
import com.ns.theendcompose.data.repository.person.PersonRepository
import javax.inject.Inject

class GetPersonExternalIdsUseCaseImpl @Inject constructor(
    private val personRepository: PersonRepository
) {
    suspend operator fun invoke(
        personId: Int,
        deviceLanguage: DeviceLanguage
    ): ApiResponse<ExternalIds> {
        return personRepository.getExternalIds(personId = personId, deviceLanguage = deviceLanguage)
            .awaitApiResponse()
    }

}