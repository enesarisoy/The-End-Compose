package com.ns.theendcompose.data.repository.person

import com.ns.theendcompose.data.model.CombinedCredits
import com.ns.theendcompose.data.model.DeviceLanguage
import com.ns.theendcompose.data.model.ExternalIds
import com.ns.theendcompose.data.model.PersonDetails
import com.ns.theendcompose.data.remote.api.others.TmdbOthersApiHelper
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonRepositoryImpl @Inject constructor(
    private val apiOthersApiHelper: TmdbOthersApiHelper
) : PersonRepository {
    override fun getPersonDetails(
        personId: Int,
        deviceLanguage: DeviceLanguage
    ): Call<PersonDetails> {
        return apiOthersApiHelper.getPersonDetails(personId, deviceLanguage.languageCode)
    }

    override fun getCombinedCredits(
        personId: Int,
        deviceLanguage: DeviceLanguage
    ): Call<CombinedCredits> {
        return apiOthersApiHelper.getCombinedCredits(personId, deviceLanguage.languageCode)
    }

    override fun getExternalIds(personId: Int, deviceLanguage: DeviceLanguage): Call<ExternalIds> {
        return apiOthersApiHelper.getPersonExternalIds(personId, deviceLanguage.languageCode)
    }
}