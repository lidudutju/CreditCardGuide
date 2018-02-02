package lidu.me.kotlindemo.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ListView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import lidu.me.kotlindemo.CommonUI.titleLayout
import lidu.me.kotlindemo.R
import lidu.me.kotlindemo.adapter.CardListAdapter
import lidu.me.kotlindemo.model.CardListItemModel
import lidu.me.kotlindemo.network.TaskRepository
import org.jetbrains.anko.*

/**
 * Created by lidu on 2018/1/11.
 */
class CardListFragment : BaseFragment() {

    private lateinit var list: List<CardListItemModel>
    private lateinit var listView: ListView

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        relativeLayout {
            backgroundColor = resources.getColor(R.color.customWhite)

            titleLayout(title = "首页")

            listView {
                listView = this
                divider = null
            }.lparams(width = wrapContent, height = matchParent) {
                topMargin = dip(50)
            }

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData()

    }

    private fun loadData() {
        launch(UI) {
            val data = TaskRepository.getList(getRandomInt().toString())
            data.let {
                if (data?.data?.list != null) {
                    list = data.data.list
                    refreshUi()
                }

            }
        }
    }

    private fun getRandomInt(): Int = (Math.random() * 30).toInt()

    private fun refreshUi() {
        val adapter = CardListAdapter(context, list)
        listView.adapter = adapter
    }

    companion object {
        const val TAG = "cardList"
    }

}