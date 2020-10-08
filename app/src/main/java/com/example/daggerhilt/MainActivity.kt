package com.example.daggerhilt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("jalil ${someClass.mFunc1()}")
        println("jalil ${someClass.mFunc2()}")

    }
}

@ActivityScoped
class SomeClass @Inject constructor(
    @Imp1 private val someInterface1: SomeInterface,
    @Imp2 private val someInterface2: SomeInterface
) {
    fun mFunc1(): String {
        return "shit it is ${someInterface1.didIt()}"
    }

    fun mFunc2(): String {
        return "shit it is ${someInterface2.didIt()}"
    }
}

class Implementation1 @Inject constructor() : SomeInterface {
    override fun didIt(): String {
        return "implementation one"
    }
}

class Implementation2 @Inject constructor() : SomeInterface {
    override fun didIt(): String {
        return "implementation two"
    }
}

interface SomeInterface {
    fun didIt(): String
}

@InstallIn(ApplicationComponent::class)
@Module
class MyModule {

    @Imp1
    @Singleton
    @Provides
    fun provideInterface1(): SomeInterface {
        return Implementation1()
    }

    @Imp2
    @Singleton
    @Provides
    fun provideInterface2(): SomeInterface {
        return Implementation2()
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Imp1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Imp2