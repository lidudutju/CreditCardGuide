package lidu.me.creditcardguide.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ListView
import kotlinx.coroutines.experimental.launch
import lidu.me.creditcardguide.CommonUI.titleLayout
import lidu.me.creditcardguide.R
import lidu.me.creditcardguide.adapter.ThreadListAdapter
import lidu.me.creditcardguide.model.ThreadItemModel
import lidu.me.creditcardguide.network.TaskRepository
import lidu.me.creditcardguide.widget.WhiteTitleBar
import org.jetbrains.anko.*

/**
 * Created by lidu on 2018/1/31.
 */
class ThreadListActivity : BaseActivity() {

    private lateinit var fid: String

    private lateinit var threadListView: ListView
    private lateinit var threadListData: List<ThreadItemModel>

    private lateinit var titleLayout: WhiteTitleBar

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            relativeLayout {
                backgroundColor = resources.getColor(R.color.customWhite)

                titleLayout("讨论区")

                threadListView = listView {

                }.lparams {
                    topMargin = dip(50.5f)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fid = intent?.getStringExtra(INTENT_KEY_FID) ?: "0"

        launch(kotlinx.coroutines.experimental.android.UI) {
            val data = TaskRepository.getThreadList(fid, "1", "15")
            data.let {
                if (data?.data?.list != null) {
                    threadListData = data.data.list
                    refreshUI()
                }

            }
            refreshUI()
        }
    }

    private fun refreshUI() {
        val adapter = ThreadListAdapter(ctx, threadListData)
        threadListView.adapter = adapter
    }

    companion object {
        const val INTENT_KEY_FID = "fid"
    }

}