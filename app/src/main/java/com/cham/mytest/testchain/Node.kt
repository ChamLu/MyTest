package com.cham.mytest.testchain

import android.util.Log

/**
 *
 * 简单二叉树
 *
 * */
class Node(var id: Int) {

    companion object {
        val TAG = "Node"
    }

    var left: Node? = null
    var right: Node? = null


    fun dfs() {
        Log.e(TAG, "$id")

        left?.dfs()


        right?.dfs()


    }

}