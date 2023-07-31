package eu.giant.kaizen.platform

import android.app.Application
import com.daryse.daryse.util.logging.HyperlinkedTree
import dagger.hilt.android.HiltAndroidApp
import eu.giant.kaizen.di.ApplicationCoroutineScope
import eu.giant.kaizer.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {

    @Inject
    @ApplicationCoroutineScope
    lateinit var coroutineScope: CoroutineScope

    companion object {
        lateinit var instance: Application private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(HyperlinkedTree())
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        coroutineScope.cancel()
    }

}
