package com.oborodulin.home.common.di

import android.content.Context
import com.oborodulin.home.common.util.ResourcesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {
    /**
     * https://www.codegrepper.com/code-examples/javascript/android+object+to+json+string
     */
    @Singleton
    @Provides
    fun provideJsonLogger(): Json = Json

    @Singleton
    @Provides
    fun provideResourcesHelper(@ApplicationContext applicationContext: Context): ResourcesHelper =
        ResourcesHelper(applicationContext)

}