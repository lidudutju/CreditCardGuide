package lidu.me.creditcardguide.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ListView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import lidu.me.creditcardguide.CommonUI.pullToRefreshListView
import lidu.me.creditcardguide.CommonUI.titleLayout
import lidu.me.creditcardguide.R
import lidu.me.creditcardguide.adapter.AnkoListAdapter
import lidu.me.creditcardguide.adapter.CardListAdapter
import lidu.me.creditcardguide.model.CardListItemModel
import lidu.me.creditcardguide.network.TaskRepository
import lidu.me.creditcardguide.widget.PullToRefreshListView
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.toast

/**
 * Created by lidu on 2018/1/11.
 */
class CardListFragment : BaseFragment() {

    private val dataList: ArrayList<CardListItemModel> = ArrayList(32)
    private lateinit var adapter: AnkoListAdapter<CardListItemModel, AnkoViewHolder<CardListItemModel>>
    private lateinit var listView: ListView

    private var page: Int = 1

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        relativeLayout {
            backgroundColor = resources.getColor(R.color.customWhite)

            titleLayout(title = "首页")

            pullToRefreshListView {
                setMode(PullToRefreshListView.Mode.BOTH)
                listView = getRefreshableView()
                setOnRefreshListener(onRefreshListener)
            }.lparams(width = wrapContent, height = matchParent) {
                topMargin = dip(50)
            }

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = CardListAdapter(context, dataList)
        listView.adapter = adapter
        loadData()
    }

    private fun loadData() {
        launch(UI) {
            val data = TaskRepository.getList(page.toString())
            data.let {
                if (data?.data?.list != null) {
                    dataList.addAll(data.data.list)
                    adapter.updateData(dataList)
                }

            }
        }
    }

    companion object {
        const val TAG = "cardList"
    }

    private val onRefreshListener = object : PullToRefreshListView.OnRefreshListener {
        override fun onRefresh(listView: ListView) {
            page = 1
            dataList.clear()
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