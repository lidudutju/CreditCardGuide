package lidu.me.kotlindemo.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import lidu.me.kotlindemo.R
import lidu.me.kotlindemo.Utils
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoLogger

/**
 * Created by lidu on 2018/1/11.
 */
abstract class BaseActivity : AppCompatActivity(), AnkoComponent<Context>, AnkoLogger {

    abstract override fun createView(ui: AnkoContext<Context>): View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Utils.tintSystemBar(this,
                resources.getColor(R.color.customWhite))

        setContentView(createView(AnkoContext.create(this)))

    }

}