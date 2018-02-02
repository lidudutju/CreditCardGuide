package lidu.me.kotlindemo

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

/**
 * Created by lidu on 2018/1/11.
 */
class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}