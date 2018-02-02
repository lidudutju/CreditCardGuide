package lidu.me.creditcardguide.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ListView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import lidu.me.creditcardguide.CommonUI.titleLayout
import lidu.me.creditcardguide.R
import lidu.me.creditcardguide.adapter.PostListAdapter
import lidu.me.creditcardguide.model.PostListItemModel
import lidu.me.creditcardguide.model.ThreadDetailModel
import lidu.me.creditcardguide.network.TaskRepository
import lidu.me.creditcardguide.widget.ThreadHeaderViewHolder
import org.jetbrains.anko.*

/**
 * Created by lidu on 2018/2/1.
 */
class ThreadDetailActivity : BaseActivity() {

    private lateinit var tid: String

    private lateinit var model: ThreadDetailModel
    private lateinit var postList: List<PostListItemModel>

    private lateinit var postListView: ListView

    private lateinit var threadHeaderViewHolder: ThreadHeaderViewHolder

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            verticalLayout {
                titleLayout("帖子详情")

                listView {
                    postListView = this
                    divider = null
                    backgroundColor = resources.getColor(R.color.customWhite)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tid = intent.getStringExtra(INTENT_KEY_TID)
        loadData()
    }

    private fun loadData() {
        launch(UI) {
            val posts = TaskRepository.getPostList("1", "10", "2", tid)
            posts.let {
                if (posts?.data?.list != null) {
                    postList = posts.data.list
                    refreshUI()
                }

            }

            val data = TaskRepository.getThreadDetail(tid)
            data.let {
                if (data?.data != null) {
                    model = data.data
                    refreshHeaderView()
                }

            }
        }
    }

    private fun refreshHeaderView() {
        threadHeaderViewHolder = ThreadHeaderViewHolder(ctx)
        postListView.addHeaderView(threadHeaderViewHolder.createView(AnkoContext.create(ctx)))
        threadHeaderViewHolder.bindData(model)
    }

    private fun refreshUI() {
        val adapter = PostListAdapter(ctx, postList)
        postListView.adapter = adapter
    }

    companion object {
        const val INTENT_KEY_TID = "tid"
    }

}