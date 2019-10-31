package ndn.base.nguyen.data.source.repositories

import ndn.base.nguyen.data.source.remote.HandyApi
import ndn.base.nguyen.data.source.local.sharedprf.SharedPrefs

interface HandyRepository {

}

class HandyRepositoryImpl(
    private val handyApi: HandyApi,
    private val sharedPrefs: SharedPrefs
) : HandyRepository {

}
