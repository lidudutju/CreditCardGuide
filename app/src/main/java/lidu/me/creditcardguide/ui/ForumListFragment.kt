package lidu.me.creditcardguide.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.GridView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import lidu.me.creditcardguide.CommonUI.titleLayout
import lidu.me.creditcardguide.R
import lidu.me.creditcardguide.adapter.ForumListAdapter
import lidu.me.creditcardguide.model.ForumItemModel
import lidu.me.creditcardguide.network.TaskRepository
import org.jetbrains.anko.*

/**
 * Created by lidu on 2018/1/30.
 */
class ForumListFragment : BaseFragment() {

    private lateinit var forumList: List<ForumItemModel>
    private lateinit var forumGridView: GridView

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            relativeLayout {
                backgroundColor = resources.getColor(R.color.customWhite)

                titleLayout(title = "论坛")

                forumGridView = gridView {
                    visibility = View.VISIBLE
                    numColumns = 3
                    isVerticalScrollBarEnabled = false
                }.lparams(width = wrapContent, height = matchParent) {
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
}