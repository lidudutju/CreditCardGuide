package lidu.me.creditcardguide.widget

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ListView
import com.airbnb.lottie.LottieAnimationView

/**
 * Created by lidu on 2018/2/8.
 */
class PullToRefreshListView(private val ctx: Context, attrs: AttributeSet?, defStyle: Int) : LinearLayout(ctx, attrs, defStyle) {

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)

    constructor(ctx: Context) : this(ctx, null)

    private var touchSlop: Int = 0
    private var isBeingDragged: Boolean = false

    private var lastMotionY: Float = 0f
    private var initialMotionY: Float = 0f

    private var lastMotionX: Float = 0f
    private var initialMotionX: Float = 0f
    private var pullScrollSize: Int = 0

    private lateinit var refreshableView: ListView
    private lateinit var refreshableViewWrapper: FrameLayout

    private var headerLayout: View = getDefaultHeaderLoading()
    private var footerLayout: View = getDefaultFooterLoading()

    private var onRefreshListener: OnRefreshListener? = null

    private var smoothScrollRunnable: SmoothScrollRunnable? = null

    private var mode: Mode = Mode.getDefault()
    private var currentMode: Mode = Mode.PULL_FROM_START
    private var currentState: State = State.RESET

    init {
        initLayout()
    }

    public fun setHeaderLayout(header: View) {
        headerLayout = header
        updateLoadingView()
    }

    public fun setFooterLayout(footer: View) {
        footerLayout = footer
        updateLoadingView()
    }

    public fun setMode(mode: Mode) {
        this.mode = mode
        updateLoadingView()
    }

    public fun getRefreshableView(): ListView {
        return refreshableView
    }

    public fun setOnRefreshListener(listener: OnRefreshListener) {
        onRefreshListener = listener
    }

    public fun onRefreshComplete() {
        if (isRefreshing()) {
            setState(State.RESET)
        }
    }

    private fun initLayout() {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER

        touchSlop = ViewConfiguration.get(ctx).scaledTouchSlop

        addRefreshableListView()

        updateLoadingView()

    }

    private fun updateLoadingView() {
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER

        headerLayout?.let {
            if (it.parent == this@PullToRefreshListView) {
                removeView(it)
            }

            if (mode.showHeaderLoadingLayout()) {
                addView(it, 0, it.layoutParams ?: layoutParams)
            }
        }

        footerLayout?.let {
            if (it.parent == this@PullToRefreshListView) {
                removeView(it)
            }
            if (mode.showFooterLoadingLayout()) {
                addView(it, -1, it.layoutParams ?: layoutParams)
            }
        }

        currentMode = if (mode == Mode.BOTH) Mode.PULL_FROM_START else Mode.BOTH

        refreshLoadingViewSize()
    }

    private fun getDefaultHeaderLoading(): View {
        val header = LottieAnimationView(ctx)
        header.setAnimation("refreshing.json")
        header.loop(true)
        header.playAnimation()
        return header
    }


    private fun getDefaultFooterLoading(): View {
        val footer = LottieAnimationView(ctx)
        footer.setAnimation("loading_rainbow.json")
        footer.loop(true)
        footer.playAnimation()
        return footer
    }

    private fun refreshLoadingViewSize() {
        val maxPullScroll = (getMaxPullScroll() * 0.2f).toInt()

        val pTop = if (mode.showHeaderLoadingLayout()) {
            setHeight(headerLayout, maxPullScroll)
            -maxPullScroll
        } else {
            0
        }

        val pBottom = if (mode.showFooterLoadingLayout()) {
            setHeight(footerLayout, maxPullScroll)
            -maxPullScroll
        } else {
            0
        }

        setPadding(paddingLeft, pTop, paddingRight, pBottom)
    }

    protected fun refreshRefreshableViewSize(w: Int, h: Int) {
        val lp = refreshableViewWrapper.layoutParams
        if (lp.height != h) {
            lp.height = h
            refreshableViewWrapper.requestLayout()
        }

    }

    private fun setHeight(view: View?, height: Int) {
        view?.let {
            val lp = it.layoutParams
            lp?.height = height
            it.requestLayout()
        }
    }

    private fun addRefreshableListView() {
        refreshableView = ListView(ctx)

        refreshableViewWrapper = FrameLayout(ctx)
        refreshableViewWrapper.addView(refreshableView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)

        addView(refreshableViewWrapper, -1, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))

    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {

        val action = ev?.actionMasked

        if (action == MotionEvent.ACTION_CANCEL
                || action == MotionEvent.ACTION_UP) {
            isBeingDragged = false
            return false
        }

        if (action != MotionEvent.ACTION_DOWN
                && isBeingDragged) {
            return true
        }

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                if (isReadyForPull()) {
                    lastMotionY = ev.y
                    lastMotionX = ev.x
                    initialMotionY = ev.y
                    initialMotionX = ev.x
                    isBeingDragged = false
                }
            }

            MotionEvent.ACTION_MOVE -> {
                if (isReadyForPull()) {
                    val diff = ev.y - lastMotionY
                    val oppositeDiff = ev.x - lastMotionX

                    val absDiff = Math.abs(diff)

                    if (absDiff > touchSlop && absDiff > Math.abs(oppositeDiff)) {
                        if (mode.showHeaderLoadingLayout() && diff >= 1f && isReadyForPullStart()) {
                            lastMotionY = ev.y
                            lastMotionX = ev.x
                            isBeingDragged = true
                            if (mode == Mode.BOTH) {
                                currentMode = Mode.PULL_FROM_START
                            }
                        } else if (mode.showFooterLoadingLayout() && diff <= -1f && isReadyForPullEnd()) {
                            lastMotionY = ev.y
                            lastMotionX = ev.x
                            isBeingDragged = true
                            if (mode == Mode.BOTH) {
                                currentMode = Mode.PULL_FROM_END
                            }
                        }
                    }
                }
            }
        }

        return isBeingDragged
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        val action = ev?.actionMasked

        if (action == MotionEvent.ACTION_DOWN
                && ev.edgeFlags != 0) {
            return false
        }

        when (action) {
            MotionEvent.ACTION_MOVE -> {
                if (isBeingDragged) {
                    lastMotionY = ev.y
                    lastMotionX = ev.x
                    pullEvent()
                    return true
                }
            }
            MotionEvent.ACTION_DOWN -> {
                if (isReadyForPull()) {
                    lastMotionX = ev.x
                    lastMotionY = ev.y
                    initialMotionX = ev.x
                    initialMotionY = ev.y
                    return true
                }
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                if (isBeingDragged) {
                    isBeingDragged = false

                    if (currentState == State.RELEASE_TO_REFRESH) {
                        if (currentMode == Mode.PULL_FROM_START) {
                            onRefreshListener?.onRefresh(getRefreshableView())
                        } else if (currentMode == Mode.PULL_FROM_END) {
                            onRefreshListener?.onLoadMore(getRefreshableView())
                        }
                        setState(State.REFRESHING)
                        return true
                    }


                    if (isRefreshing()) {
                        smoothScrollTo(0)
                        return true
                    }

                    setState(State.RESET)
                    return true
                }
            }
        }

        return false
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        refreshLoadingViewSize()

        refreshRefreshableViewSize(w, h)

        post {
            requestLayout()
        }
    }

    private fun setState(state: State) {
        currentState = state

        when (state) {
            State.RESET -> {
                onReset()
            }
            State.PULL_TO_REFRESH -> {

            }
            State.REFRESHING -> {
                when (currentMode) {
                    Mode.PULL_FROM_END, Mode.MANUAL_REFRESH_ONLY -> {
                        smoothScrollTo(getFooterHeight())
                    }
                    else -> {
                        smoothScrollTo(-getHeaderHeight())
                    }
                }
            }
            else -> {

            }
        }
    }

    private fun onReset() {
        isBeingDragged = false
        smoothScrollTo(0)
    }

    private fun isRefreshing(): Boolean = currentState == State.REFRESHING
            || currentState == State.MANUAL_REFRESHING


    private fun pullEvent() {
        var newScrollValue = 0
        var itemHeight = 0
        newScrollValue = when (currentMode) {
            Mode.PULL_FROM_END -> {
                itemHeight = getFooterHeight()
                Math.round(Math.max(initialMotionY - lastMotionY, 0f) / FRICTION)
            }
            Mode.PULL_FROM_START -> {
                itemHeight = getHeaderHeight()
                Math.round(Math.min(initialMotionY - lastMotionY, 0f) / FRICTION)
            }
            else -> {
                itemHeight = getHeaderHeight()
                Math.round(Math.min(initialMotionY - initialMotionY, 0f) / FRICTION)
            }

        }

        setHeaderScroll(newScrollValue)

        if (newScrollValue != 0 && !isRefreshing()) {
            if (currentState != State.PULL_TO_REFRESH && itemHeight >= Math.abs(newScrollValue)) {
                setState(State.PULL_TO_REFRESH)
            } else if (currentState == State.PULL_TO_REFRESH && itemHeight < Math.abs(newScrollValue)) {
                setState(State.RELEASE_TO_REFRESH)
            }
        }

    }

    private fun getFooterHeight(): Int = headerLayout?.layoutParams?.height ?: 100

    private fun getHeaderHeight(): Int = footerLayout?.layoutParams?.height ?: 100


    fun setHeaderScroll(value: Int) {
        when {
            value > 0 -> footerLayout?.visibility = View.VISIBLE
            value < 0 -> headerLayout?.visibility = View.VISIBLE
            value == 0 -> {
                headerLayout?.visibility = View.INVISIBLE
                footerLayout?.visibility = View.INVISIBLE
            }
        }

        pullScrollSize = -value

        if (value == 0) {
            ViewCompat.setLayerType(refreshableViewWrapper, View.LAYER_TYPE_NONE, null)
        } else {
            ViewCompat.setLayerType(refreshableViewWrapper, View.LAYER_TYPE_HARDWARE, null)
        }

        scrollTo(0, value)
    }

    private fun smoothScrollTo(newScrollValue: Int, duration: Long = SMOOTH_SCROLL_DURATION_MS) {
        smoothScrollRunnable?.stop()

        if (scrollY != newScrollValue) {
            smoothScrollRunnable = SmoothScrollRunnable(scrollY, newScrollValue,
                    duration, this)
            post(smoothScrollRunnable)
        }
    }

    private fun getMaxPullScroll() = Math.round(height / FRICTION)

    private fun isReadyForPull(): Boolean {
        return when (mode) {
            Mode.PULL_FROM_START -> isReadyForPullStart()
            Mode.PULL_FROM_END -> isReadyForPullEnd()
            Mode.BOTH -> isReadyForPullStart() || isReadyForPullEnd()
            else -> return false
        }
    }

    private fun isReadyForPullEnd(): Boolean {
        val adapter = refreshableView.adapter

        if (adapter == null || adapter.isEmpty) {
            return true
        } else {
            if (refreshableView.lastVisiblePosition >= adapter.count - 1) {
                val childIndex = refreshableView.lastVisiblePosition - refreshableView.firstVisiblePosition
                val child = refreshableView.getChildAt(childIndex)
                if (child != null) {
                    return child.bottom <= refreshableView.bottom
                }
            }
        }
        return false
    }


    private fun isReadyForPullStart(): Boolean {
        val adapter = refreshableView.adapter

        if (adapter == null || adapter.isEmpty) {
            return true
        } else {
            if (refreshableView.firstVisiblePosition == 0) {
                val child = refreshableView.getChildAt(0)
                if (child != null) {
                    return child.top >= refreshableView.top
                }
            }
        }
        return false
    }

    enum class Mode(val mode: Int) {

        /**
         * Disable all Pull-to-Refresh gesture and Refreshing handling
         */
        DISABLED(0x0),

        /**
         * Only allow the user to Pull from the start of the Refreshable View to refresh. The start is either the Top or
         * Left, depending on the scrolling direction.
         */
        PULL_FROM_START(0x1),

        /**
         * Only allow the user to Pull from the end of the Refreshable View to refresh. The start is either the Bottom
         * or Right, depending on the scrolling direction.
         */
        PULL_FROM_END(0x2),

        /**
         * Allow the user to both Pull from the start, from the end to refresh.
         */
        BOTH(0x3),

        /**
         * Disables Pull-to-Refresh gesture handling, but allows manually setting the Refresh state via
         * [setRefreshing()][PullToRefreshBase.setRefreshing].
         */
        MANUAL_REFRESH_ONLY(0x4);

        companion object {
            fun getDefault(): Mode {
                return Mode.PULL_FROM_START
            }

        }

        fun showHeaderLoadingLayout(): Boolean = this == Mode.PULL_FROM_START || this == Mode.BOTH

        fun showFooterLoadingLayout(): Boolean = this == Mode.PULL_FROM_END || this == Mode.BOTH

    }

    enum class State(val state: Int) {
        /**
         * When the UI is in a state which means that user is not interacting with the Pull-to-Refresh function.
         */
        RESET(0x0),

        /**
         * When the UI is being pulled by the user, but has not been pulled far enough so that it refreshes when
         * released.
         */
        PULL_TO_REFRESH(0x1),

        /**
         * When the UI is being pulled by the user, and **has** been pulled far enough so that it will
         * refresh when released.
         */
        RELEASE_TO_REFRESH(0x2),

        /**
         * When the UI is currently refreshing, caused by a pull gesture.
         */
        REFRESHING(0x8),

        /**
         * When the UI is currently refreshing, caused by a call to [ setRefreshing()][PullToRefreshBase.setRefreshing].
         */
        MANUAL_REFRESHING(0x9),

        /**
         * When the UI is currently overscrolling, caused by a fling on the Refreshable View.
         */
        OVERSCROLLING(0x10);
    }

    public interface OnRefreshListener {
        fun onRefresh(listView: ListView)
        fun onLoadMore(listView: ListView)
    }

    companion object {
        const val FRICTION = 2.8f
        const val SMOOTH_SCROLL_DURATION_MS = 200L

    }

    class SmoothScrollRunnable(
            private val scrollFromY: Int,
            private val scrollToY: Int,
            private val duration: Long,
            private val pullToRefreshView: PullToRefreshListView
    ) : Runnable {
        private val interpolator = DecelerateInterpolator()

        private var startTime: Long = -1L
        private var currentY: Int = -1

        private var continueRunning = true

        override fun run() {
            if (startTime == -1L) {
                startTime = System.currentTimeMillis()
            } else {
                var normalizedTime = 1000 * (System.currentTimeMillis() - startTime) / duration
                normalizedTime = Math.max(Math.min(normalizedTime, 1000), 0)

                val deltaY = Math.round((scrollFromY - scrollToY)
                        * interpolator.getInterpolation(normalizedTime / 1000f))
                currentY = scrollFromY - deltaY
                pullToRefreshView.setHeaderScroll(currentY)
            }

            if (continueRunning && scrollToY != currentY) {
                pullToRefreshView.postOnAnimation(this)
            }
        }

        fun stop() {
            continueRunning = false
            pullToRefreshView.removeCallbacks(this)
        }

    }

}