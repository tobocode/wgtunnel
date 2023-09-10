package com.zaneschepke.wireguardautotunnel

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.mlkit.common.MlKit
import com.zaneschepke.wireguardautotunnel.repository.Repository
import com.zaneschepke.wireguardautotunnel.service.tunnel.model.Settings
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class WireGuardAutoTunnel : Application() {

    @Inject
    lateinit var settingsRepo : Repository<Settings>

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false);
            Timber.plant(Timber.DebugTree())
        }
        try {
            MlKit.initialize(this)
        } catch (e : Exception) {
            Timber.e(e.message)
        }
        settingsRepo.init()
    }

    companion object {
        fun isRunningOnAndroidTv(context : Context) : Boolean {
            return context.packageManager.hasSystemFeature(PackageManager.FEATURE_LEANBACK)
        }
    }
}