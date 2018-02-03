package br.com.conseng.basicloadmore

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.OnScrollListener
import android.view.View
import android.widget.AbsListView
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: Adapter
    private lateinit var list: ArrayList<String>
    private lateinit var progressBar: ProgressBar

    private var isScrolling = false
    private var currentItems = 0
    private var totalItems = 0
    private var scrollOutItems = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = lo_progress

        list = arrayListOf("25", "91", "65", "4", "60", "67", "56", "1", "80", "38", "29", "97", "11", "71", "59")
        adapter = Adapter(list, this)

        recyclerView = lo_recycler_view
        recyclerView.adapter = adapter

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        val listener = ScrollListener()
        recyclerView.addOnScrollListener(listener)
    }

    /**
     * Creates a runnable from a function
     */
    fun r(f: () -> Unit): Runnable = object : Runnable {
        override fun run() {
            f()
        }
    }

    private fun fetchData() {
        progressBar.visibility= View.VISIBLE
        val runnable = r {
            for (i in 0..5) {
                list.add(Math.floor(Math.random() * 100).toString())
                adapter.notifyDataSetChanged()
            }
            progressBar.visibility= View.GONE
        }
        val handler = Handler().postDelayed(runnable, 5000)
    }

    /**
     * Receive messages when a scrolling event has occurred on that RecyclerView.
     * Add more elements at the end of the ArrayList using random values.
     */
    inner class ScrollListener : OnScrollListener() {
        /**
         * Callback method to be invoked when RecyclerView's scroll state changes.
         * @param [recyclerView] The RecyclerView whose scroll state has changed.
         * @param [newState]     The updated scroll state. One of:
         *                       .SCROLL_STATE_IDLE,
         *                       .SCROLL_STATE_DRAGGING or
         *                       .SCROLL_STATE_SETTLING.
         */
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL == newState) {
                isScrolling = true
            }
        }

        /**
         * Callback method to be invoked when the RecyclerView has been scrolled.
         * This will be called after the scroll has completed.
         * This callback will also be called if visible item range changes after a layout
         * calculation. In that case, dx and dy will be 0.
         *
         * @param [recyclerView] The RecyclerView which scrolled.
         * @param [dx] The amount of horizontal scroll.
         * @param [dy] The amount of vertical scroll.
         */
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            currentItems = linearLayoutManager.childCount
            totalItems = linearLayoutManager.itemCount
            scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition()

            if (isScrolling and ((currentItems + scrollOutItems) == totalItems)) {
                isScrolling = false
                fetchData()
            }
        }
    }
}


