package lidu.me.creditcardguide.widget

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.dip

/**
 * Created by lidu on 2018/1/30.
 */
class HomeTabView(private val ctx: Context,
                  private val callback: HomeTabSelectCallback,
                  private val iconResList: List<Int>,
                  private val selectIconResList: List<Int>,
                  private val textResList: List<Int>) : LinearLayout(ctx) {

    private var homeTab = HomeTabItemView(context, iconResList[0], selectIconResList[0], textResList[0])
    private var forumTab = HomeTabItemView(context, iconResList[1], selectIconResList[1], textResList[1])
    private var favoriteTab = HomeTabItemView(context, iconResList[2], selectIconResList[2], textResList[2])

    private lateinit var childZero: View
    private lateinit var childOne: View
    private lateinit var childTwo: View

    init {
        val params = LinearLayout.LayoutParams(dip(0), dip(50), 1f)
        var child: View? = null
        for (index in 0..2) {
            when (index) {
                0 -> {
                    childZero = homeTab.createView(AnkoContext.create(ctx, this@HomeTabView))
                    child = childZero
                }
                1 -> {
                    childOne = forumTab.createView(AnkoContext.create(ctx, this@HomeTabView))
                    child = childOne
                }
                2 -> {
                    childTwo = favoriteTab.createView(AnkoContext.create(ctx, this@HomeTabView))
                    child = childTwo

                }
            }
            addView(child, index, params)
        }

        initListener()
        orientation = LinearLayout.HORIZONTAL
    }

    public fun setSelectPos(position: Int) {
        when (position) {
            0 -> {
                callback.onTabSelect(0)
                homeTab.setSelectState(true)
                forumTab.setSelectState(false)
                favoriteTab.setSelectState(false)
            }
            1 -> {
                callback.onTabSelect(1)
                homeTab.setSelectState(false)
                forumTab.setSelectState(true)
                favoriteTab.setSelectState(false)
            }
            2 -> {
                callback.onTabSelect(2)
                homeTab.setSelectState(false)
                forumTab.setSelectState(false)
                favoriteTab.setSelectState(true)
            }
        }
    }

    private fun initListener() {
        childZero.setOnClickListener {
            setSelectPos(0)
        }

        childOne.setOnClickListener {
            setSelectPos(1)
        }

        childTwo.setOnClickListener {
            setSelectPos(2)
        }
    }

    interface HomeTabSelectCallback {
        fun onTabSelect(position: Int)
    }

}