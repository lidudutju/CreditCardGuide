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
import lidu.me.creditcardguide.adapter.PostListAdapter
import lidu.me.creditcardguide.model.PostListItemModel
import lidu.me.creditcardguide.model.ThreadDetailModel
import lidu.me.creditcardguide.network.TaskRepository
import lidu.me.creditcardguide.widget.PullToRefreshListView
import lidu.me.creditcardguide.widget.ThreadHeaderViewHolder
import lidu.me.creditcardguide.widget.WhiteTitleBar
import org.jetbrains.anko.*

/**
 * Created by lidu on 2018/2/1.
 */
class ThreadDetailActivity : BaseActivity() {

    private var tid: String? = null
    private var imageList: ArrayList<String>? = null

    private lateinit var model: ThreadDetailModel
    private var postList: ArrayList<PostListItemModel> = ArrayList(16)

    private lateinit var postListView: ListView
    private lateinit var titleBar: WhiteTitleBar

    private lateinit var threadHeaderViewHolder: ThreadHeaderViewHolder
    private lateinit var adapter: AnkoListAdapter<PostListItemModel, AnkoViewHolder<PostListItemModel>>

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            verticalLayout {
                titleLayout(title = "帖子详情") {
                    titleBar = it
                }.lparams {
                    leftMargin = dip(15)
                }

                pullToRefreshListView {
                    postListView = getRefreshableView()
                    postListView.divider = null
                    setOnRefreshListener(onRefreshListener)
                    backgroundColor = resources.getColor(R.color.customWhite)
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.getBundleExtra(INTENT_KEY_BUNDLE)
        tid = bundle.getString(INTENT_KEY_TID)
        imageList = bundle.getStringArrayList(INTENT_KEY_IMAGE_LIST)

        titleBar.showBackButton(View.OnClickListener { finish() })

        adapter = PostListAdapter(ctx, postList)
        postListView.adapter = adapter

        loadData()
    }

    private fun loadData() {
        launch(UI) {
            val posts = TaskRepository.getPostList("1", "10", "2", tid ?: "0")
            posts?.let {
                postList.addAll(posts.data.list)
                adapter.updateData(postList)

            }

            val data = TaskRepository.getThreadDetail(tid ?: "0")
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

        imageList?.let {
            threadHeaderViewHolder.setImageList(it)
        }
    }

    companion object {
        const val INTENT_KEY_TID = "tid"
        const val INTENT_KEY_IMAGE_LIST = "image_list"
        const val INTENT_KEY_BUNDLE = "bundle"
    }

    private val onRefreshListener = object : PullToRefreshListView.OnRefreshListener {
        override fun onRefresh(listView: ListView) {
//            page = 1
//            postList.clear()
//            loadData()
            toast("on refresh!!")
        }

        override fun onLoadMore(listView: ListView) {
//            page++
//            loadData()
            toast("on load more!!")
        }

    }

}