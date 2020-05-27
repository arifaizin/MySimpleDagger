package com.dicoding.mysimpledagger

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class AppModule {
    @Provides
    fun provideEngine(context: Context): Engine = Engine(context)
}

@Component(modules = [AppModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
}

open class MyApplication : Application(){
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(
            applicationContext
        )    }
}

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var car: Car

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as MyApplication).appComponent.inject(this)

        car.start()
    }
}

class Car @Inject constructor(private val engine: Engine) {
    fun start() {
        engine.start()
    }
}

class Engine(val context: Context) {
    fun start() {
        println(context.applicationContext.getString(R.string.engine_start))
    }
}
