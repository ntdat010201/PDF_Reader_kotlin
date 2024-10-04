package com.example.pdfreader_kotlin.base

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ComponentActivity
import com.example.pdfreader_kotlin.di.listModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.androidx.scope.LifecycleScopeDelegate
import org.koin.androidx.scope.activityScope
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

open class BaseActivity : AppCompatActivity(),AndroidScopeComponent {
    override val scope by contextAwareActivityScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupKoinFragmentFactory(scope)
    }

    private fun ComponentActivity.contextAwareActivityScope() = runCatching {
        LifecycleScopeDelegate<Activity>(
            lifecycleOwner = this, koin = getKoin()
        )
    }.getOrElse { activityScope() }

    private fun ComponentActivity.getKoin(): Koin {
        return if (this is KoinComponent) {
            getKoin()
        } else {
            GlobalContext.getOrNull() ?: startKoin {
                androidContext(applicationContext)
                modules(listModule)
            }.koin
        }
    }
}