package ndn.base.nguyen.di

import android.app.Application
import ndn.base.nguyen.data.source.remote.HandyApi
import ndn.base.nguyen.data.source.local.sharedprf.SharedPrefs
import ndn.base.nguyen.data.source.local.sharedprf.SharedPrefsImpl
import ndn.base.nguyen.data.source.repositories.HandyRepository
import ndn.base.nguyen.data.source.repositories.HandyRepositoryImpl
import ndn.base.nguyen.data.source.repositories.TokenRepository
import ndn.base.nguyen.data.source.repositories.TokenRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val repositoryModule = module {
    single { provideTokenRepository(androidApplication()) }
    single { provideHandyRepository(get(), get()) }
}

fun provideTokenRepository(app: Application): TokenRepository {
    return TokenRepositoryImpl(
            SharedPrefsImpl(app)
    )
}

fun provideHandyRepository(api: HandyApi, sharedPrefs: SharedPrefs): HandyRepository = HandyRepositoryImpl(api, sharedPrefs)
