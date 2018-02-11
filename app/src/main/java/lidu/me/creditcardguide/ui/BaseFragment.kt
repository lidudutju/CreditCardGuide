package lidu.me.creditcardguide.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoLogger


/**
 * Created by lidu on 2018/1/11.
 */
abstract class BaseFragment : Fragment(), AnkoComponent<Context>, AnkoLogger {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(context!!))
    }

    abstract override fun createView(ui: AnkoContext<Context>): View
}