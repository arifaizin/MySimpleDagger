package com.dicoding.mysimpledagger

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class AppModule {
    @Provides
    fun provideEngine(): Engine = Engine()
}

@Component(modules = [AppModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }

    fun inject(activity: MainActivity)
}

open class MyApplication : Application(){
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create()
    }
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

class Engine {
    fun start() {
        println("Engine started....")
    }
}
