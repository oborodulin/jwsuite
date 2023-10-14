package com.oborodulin.home.common.di

import com.oborodulin.home.common.secure.AesCipherProvider
import com.oborodulin.home.common.secure.CipherProvider
import com.oborodulin.home.common.secure.Crypto
import com.oborodulin.home.common.secure.CryptoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.security.KeyStore
import javax.inject.Named

// https://blog.stylingandroid.com/datastore-security/
@Module(includes = [SecurityModule.Declarations::class])
//@InstallIn(ApplicationComponent::class)
@InstallIn(SingletonComponent::class)
object SecurityModule {
    const val KEY_NAME = "Key Name"
    const val KEY_STORE_NAME = "Key Store Name"

    private const val AUTH_DATA_KEY_NAME = "AuthDataKey"
    private const val ANDROID_KEY_STORE_TYPE = "AndroidKeyStore"

    @Provides
    fun provideKeyStore(): KeyStore =
        KeyStore.getInstance(ANDROID_KEY_STORE_TYPE).apply { load(null) }

    // https://stackoverflow.com/questions/69992306/what-is-named-in-kotlin
    // https://medium.com/@WindRider/correct-usage-of-dagger-2-named-annotation-in-kotlin-8ab17ced6928
    @Provides
    @Named(KEY_NAME)
    fun providesKeyName(): String = AUTH_DATA_KEY_NAME

    @Provides
    @Named(KEY_STORE_NAME)
    fun providesKeyStoreName(): String = ANDROID_KEY_STORE_TYPE

    @Module
//    @InstallIn(ApplicationComponent::class)
    @InstallIn(SingletonComponent::class)
    interface Declarations {
        @Binds
        fun bindsCipherProvider(impl: AesCipherProvider): CipherProvider

        @Binds
        fun bindsCrypto(impl: CryptoImpl): Crypto
    }
}