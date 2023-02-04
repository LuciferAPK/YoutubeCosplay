package com.example.cosplayyoutobe.injection

import android.content.Context
import com.example.cosplayyoutobe.navigation.NavigationManager
import com.example.cosplayyoutobe.local.PreferencesManager
import com.example.cosplayyoutobe.local.RealmManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideNavigationManager(@ApplicationContext context: Context) = NavigationManager(context)

    @Singleton
    @Provides
    fun providePreferenceManager(@ApplicationContext context: Context) = PreferencesManager(context)

    @Singleton
    @Provides
    fun provideRealmManager(
        @ApplicationContext context: Context
    ) = RealmManager(context)
}