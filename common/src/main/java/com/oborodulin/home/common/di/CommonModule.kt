package com.oborodulin.home.common.di

import android.content.Context
import com.google.gson.Gson
import com.oborodulin.home.common.util.ResourcesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {
    /**
     * https://www.codegrepper.com/code-examples/javascript/android+object+to+json+string
     */
    @Singleton
    @Provides
    fun provideJsonLogger(): Gson = Gson()

    @Singleton
    @Provides
    fun provideResourcesHelper(@ApplicationContext applicationContext: Context): ResourcesHelper =
        ResourcesHelper(applicationContext)

}