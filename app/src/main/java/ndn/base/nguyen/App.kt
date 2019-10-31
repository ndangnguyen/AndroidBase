package ndn.base.nguyen

import android.app.Activity
import android.app.Application
import com.squareup.leakcanary.LeakCanary
import ndn.base.nguyen.di.appModule
import ndn.base.nguyen.di.networkModule
import ndn.base.nguyen.di.repositoryModule
import ndn.base.nguyen.di.viewModelModule
import ndn.base.nguyen.utils.GlideApp
import ndn.base.nguyen.utils.LogUtils
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * --------------------
 * Created by ThuanPx on 8/27/2019.
 * Screen name:
 * --------------------
 */

class App : Application() {

    private var currentClass: Class<*>? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        configLeakCanary()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            androidFileProperties()
            modules(appModule, networkModule, viewModelModule, repositoryModule)
        }
    }

    override fun onLowMemory() {
        GlideApp.get(this).onLowMemory()
        super.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        GlideApp.get(this).onTrimMemory(level)
        super.onTrimMemory(level)
    }

    private fun configLeakCanary() {
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return
            }
            LeakCanary.install(this)
        }
    }

    fun setCurrentClass(clazz: Class<out Activity>) {
        currentClass = clazz
        LogUtils.d("CurrentClass: ", clazz.javaClass.simpleName)
    }

    fun getCurrentClass(): Class<*>? {
        return currentClass
    }

    companion object {
        private const val TAG = "MainApplication"
        lateinit var instance: App
    }
}