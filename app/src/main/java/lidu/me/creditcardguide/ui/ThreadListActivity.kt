package lidu.me.creditcardguide.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ListView
import kotlinx.coroutines.experimental.launch
import lidu.me.creditcardguide.CommonUI.pullToRefreshListView
import lidu.me.creditcardguide.CommonUI.titleLayout
import lidu.me.creditcardguide.R
import lidu.me.creditcardguide.adapter.AnkoListAdapter
import lidu.me.creditcardguide.adapter.ThreadListAdapter
import lidu.me.creditcardguide.model.ThreadItemModel
import lidu.me.creditcardguide.network.TaskRepository
import lidu.me.creditcardguide.widget.PullToRefreshListView
import lidu.me.creditcardguide.widget.WhiteTitleBar
import org.jetbrains.anko.*

/**
 * Created by lidu on 2018/1/31.
 */
class ThreadListActivity : BaseActivity() {

    private lateinit var fid: String

    private lateinit var threadListView: ListView
    private var threadListData: ArrayList<ThreadItemModel> = ArrayList(32)

    private lateinit var titleLayout: WhiteTitleBar
    private lateinit var adapter: AnkoListAdapter<ThreadItemModel, AnkoViewHolder<ThreadItemModel>>

    private var page: Int = 1

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            relativeLayout {
                backgroundColor = resources.getColor(R.color.customWhite)

                titleLayout("讨论区")

                pullToRefreshListView {
                    threadListView = getRefreshableView()
                    setOnRefreshListener(onRefreshListener)
                }.lparams {
                    topMargin = dip(50.5f)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fid = intent?.getStringExtra(INTENT_KEY_FID) ?: "0"

        adapter = ThreadListAdapter(ctx, threadListData)
        threadListView.adapter = adapter

        loadData()
    }

    private fun loadData() {
        launch(kotlinx.coroutines.experimental.android.UI) {
            val data = TaskRepository.getThreadList(fid, page.toString(), "15")
            data.let {
                if (data?.data?.list != null) {
                    threadListData.addAll(data.data.list)
                    adapter.updateData(threadListData)
                }

            }
        }
    }

    companion object {
        const val INTENT_KEY_FID = "fid"
    }

    private val onRefreshListener = object : PullToRefreshListView.OnRefreshListener {
        override fun onRefresh(listView: ListView) {
            page = 1
            threadListData.clear()
            loadData()
            toast("on refresh!!")
        }

        override fun onLoadMore(listView: ListView) {
            page++
            loadData()
            toast("on load more!!")
        }

    }

}