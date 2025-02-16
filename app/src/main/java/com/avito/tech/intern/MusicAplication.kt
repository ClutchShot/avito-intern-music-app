package com.avito.tech.intern

import android.app.Application
import android.content.Context
import androidx.media3.common.util.UnstableApi
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@HiltAndroidApp
class MusicAplication : Application() {

    @Inject
    @ApplicationContext
    lateinit var context: Context

}