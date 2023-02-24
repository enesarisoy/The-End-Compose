package com.ns.theendcompose.data.repository.person

import com.ns.theendcompose.data.model.CombinedCredits
import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.data.model.ExternalIds
import com.ns.theendcompose.data.model.PersonDetails
import retrofit2.Call

interface PersonRepository {
    fun getPersonDetails(
        personId: Int,
        deviceLanguage: DeviceLanguage = DeviceLanguage.default
    ) : Call<PersonDetails>

    fun getCombinedCredits(
        personId: Int,
        deviceLanguage: DeviceLanguage = DeviceLanguage.default
    ) : Call<CombinedCredits>

    fun getExternalIds(
        personId: Int,
        deviceLanguage: DeviceLanguage = DeviceLanguage.default
    ): Call<ExternalIds>
}