package lidu.me.kotlindemo.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import lidu.me.kotlindemo.CommonUI.customViewPager
import lidu.me.kotlindemo.CommonUI.homeTabView
import lidu.me.kotlindemo.R
import lidu.me.kotlindemo.widget.HomeTabView
import org.jetbrains.anko.*

/**
 * Created by lidu on 2018/1/9.
 */
class MainActivity : BaseActivity(), HomeTabView.HomeTabSelectCallback {

    private lateinit var tabView: HomeTabView
    private lateinit var pager: ViewPager
    private val fragmentList: ArrayList<Fragment> = ArrayList(3)

    override fun onTabSelect(position: Int) {
        pager.setCurrentItem(position, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(createView(AnkoContext.Companion.create(this)))

        fragmentList.add(CardListFragment())
        fragmentList.add(ForumListFragment())
        fragmentList.add(FavoriteListFragment())

        val adapter = PagerAdapter(supportFragmentManager, fragmentList)
        pager.adapter = adapter

        tabView.setSelectPos(0)
    }

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            relativeLayout {
                backgroundColor = resources.getColor(R.color.customWhite)

                pager = customViewPager {
                    id = R.id.fragment_container
                    offscreenPageLimit = 2
                }.lparams {
                    bottomMargin = dip(50)
                }

                val iconResList = listOf(R.drawable.ic_tab_home_unselect, R.drawable.ic_tab_forum_unselect, R.drawable.ic_tab_favorite_unselect)
                val selectIconResList = listOf(R.drawable.ic_tab_home_select, R.drawable.ic_tab_forum_select, R.drawable.ic_tab_favorite_select)
                val textResList = listOf(R.string.home_tab_home, R.string.home_tab_forum, R.string.home_tab_favorite)

                view {
                    backgroundColor = resources.getColor(R.color.default_divider)
                }.lparams {
                    width = matchParent
                    height = dip(0.5f)
                    alignParentBottom()
                    bottomMargin = dip(50)
                }

                tabView = homeTabView(callback = this@MainActivity, iconRes = iconResList,
                        selectIconRes = selectIconResList, textRes = textResList) {
                }.lparams {
                    alignParentBottom()
                    width = matchParent
                    height = dip(50)
                }

            }
        }
    }

    class PagerAdapter(fm: FragmentManager, val list: List<Fragment>) :
            FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return list[position]
        }

        override fun getCount(): Int {
            return list.size
        }

    }

}