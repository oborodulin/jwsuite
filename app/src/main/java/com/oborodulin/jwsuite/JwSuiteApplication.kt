package com.oborodulin.jwsuite

//import com.orhanobut.logger.FormatStrategy
//import com.orhanobut.logger.PrettyFormatStrategy
import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.oborodulin.home.common.BuildConfig
import com.oborodulin.home.common.util.Constants
import com.oborodulin.home.common.util.ReleaseTree
import com.oborodulin.home.common.util.ResourcesHelper
import com.oborodulin.home.common.util.getAppVersion
import com.oborodulin.home.common.util.setLocale
import com.oborodulin.jwsuite.data.local.db.JwSuiteDatabase
import com.oborodulin.jwsuite.domain.repositories.WorkerProviderRepository
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

private const val TAG = "JWSuiteApp"

@HiltAndroidApp
class JwSuiteApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var workerProviderRepository: WorkerProviderRepository

    init {
        app = this
    }

    /**
     *
     */
    override fun onCreate() {
        super.onCreate()
        setLocale(locale = Locale(Constants.LANGUAGE_RU))

        /*val logFormatStrategy: FormatStrategy =
            PrettyFormatStrategy.newBuilder().showThreadInfo(true).methodCount(1).methodOffset(5)
                .tag(TAG)
                .build()
        Logger.addLogAdapter(AndroidLogAdapter(logFormatStrategy))
        */
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                /*
                   @Override
                   override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                       Logger.log(priority, tag, message, t)
                   }
   */
                @Override
                override fun createStackElementTag(element: StackTraceElement): String? {
                    return String.format(
                        "Class:%s: Line: %s, Method: %s",
                        super.createStackElementTag(element),
                        element.lineNumber,
                        element.methodName
                    )
                }
            })
        } else {
            Timber.plant(ReleaseTree())
        }
        val languages =
            ResourcesHelper.getHashMapResource(this, com.oborodulin.home.common.R.xml.languages)
        val version = this.getAppVersion()
        Timber.tag(TAG)
            .i(
                "App version %s is starting [%s]",
                version?.versionName, //BuildConfig.VERSION_NAME,
                Locale.getDefault().language
            )
        Timber.tag(TAG).i("Framework (API %s)", android.os.Build.VERSION.SDK_INT)
        //Timber.tag(TAG).i("SQLite v.%s", JwSuiteDatabase.sqliteVersion())

        initialiseDagger()
        Timber.tag(TAG).i("App Initialized")
    }

    private fun initialiseDagger() {
        Timber.tag(TAG).i("Initialise Dagger")
    }

    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .build()

    /*    override fun getWorkManagerConfiguration(): Configuration =
            Configuration.Builder()
                .setMinimumLoggingLevel(if (BuildConfig.DEBUG) android.util.Log.VERBOSE else android.util.Log.ERROR)
                .build()*/

    companion object {
        private lateinit var app: JwSuiteApplication
        fun getAppContext(): Context = app.applicationContext
    }

}