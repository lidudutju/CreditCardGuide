package lidu.me.creditcardguide.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.GridView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import lidu.me.creditcardguide.CommonUI.pullToRefreshGridView
import lidu.me.creditcardguide.CommonUI.titleLayout
import lidu.me.creditcardguide.R
import lidu.me.creditcardguide.adapter.ForumListAdapter
import lidu.me.creditcardguide.model.ForumItemModel
import lidu.me.creditcardguide.network.TaskRepository
import lidu.me.creditcardguide.widget.PullToRefreshBase
import lidu.me.creditcardguide.widget.PullToRefreshGridView
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.toast

/**
 * Created by lidu on 2018/1/30.
 */
class ForumListFragment : BaseFragment() {

    private lateinit var forumList: List<ForumItemModel>
    private lateinit var forumGridView: GridView
    private lateinit var pullToRefreshView: PullToRefreshGridView

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            relativeLayout {
                backgroundColor = resources.getColor(R.color.customWhite)

                titleLayout(title = "论坛")

                pullToRefreshGridView {
                    pullToRefreshView = this@pullToRefreshGridView
                    setMode(PullToRefreshBase.Mode.PULL_FROM_START)
                    forumGridView = refreshableView
                    forumGridView.numColumns = 3
                    addOnRefreshListener(onRefreshListener)
                    forumGridView.isVerticalScrollBarEnabled = false
                }.lparams {
                    topMargin = dip(50.5f)
                    width = matchParent
                    height = matchParent
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData()
    }

    private fun loadData() {
        launch(UI) {
            val data = TaskRepository.getForumList()
            data.let {
                if (data?.data?.list != null) {
                    forumList = data.data.list
                    refreshUi()
                    pullToRefreshView.onRefreshComplete()
                }

            }
        }
    }

    private fun refreshUi() {
        val adapter = ForumListAdapter(context!!, forumList)
        forumGridView.adapter = adapter
    }

    companion object {
        const val TAG = "forumList"
    }

    private val onRefreshListener = object : PullToRefreshBase.OnRefreshListener<GridView> {

        override fun onRefresh(listView: PullToRefreshBase<GridView>) {
            loadData()
        }

        override fun onLoadMore(listView: PullToRefreshBase<GridView>) {
            toast("on load more!!")
        }

    }
}