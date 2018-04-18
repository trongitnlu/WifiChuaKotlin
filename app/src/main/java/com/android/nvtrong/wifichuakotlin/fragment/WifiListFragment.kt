package com.android.nvtrong.wifichuakotlin.fragment

import android.app.ListFragment
import android.net.wifi.ScanResult
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.android.nvtrong.wifichuakotlin.R
import com.android.nvtrong.wifichuakotlin.WifiActivity
import com.android.nvtrong.wifichuakotlin.model.WifiStation
import kotlinx.android.synthetic.main.fragment_wifi_list.*

/**
 * Created by nvtrong on 4/17/2018.
 */
class WifiListFragment() : ListFragment() {
    companion object {
        fun newInstance() = WifiListFragment()
        private var emtyView: View? = null

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_wifi_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emtyView = progress
        listAdapter = WifiListAdapter(activity)
        listView.emptyView = emtyView
    }

    override fun onResume() {
        super.onResume()
        val activity = this.activity
        if (activity is WifiActivity) {
            activity.onResumeFragment(this);
        }
    }

    override fun onListItemClick(l: ListView, v: View?, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        val activity = this.activity
        if (activity is WifiActivity) {
            val item = l.getItemAtPosition(position) as WifiStation
            activity.transitionToDetail(item)
        }
    }

    fun updateItems(stations: List<ScanResult>? = null) {
        val listAdapter = this.listAdapter
        if (listAdapter is WifiListAdapter) {
            listAdapter.clear()
            if (stations != null) {
                WifiListFragment.emtyView?.visibility = if (stations.size > 0) View.VISIBLE else View.GONE
                listAdapter.addAll(WifiStation.newList(stations))
            }
            listAdapter.notifyDataSetChanged()
        }
    }

    /**
     * Clears adapter items and calls notify.
     */
    fun clearItems() = updateItems()

}