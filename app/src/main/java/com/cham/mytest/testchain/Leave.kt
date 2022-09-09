package com.cham.mytest.testchain

class Leave(private val name: String, private val age: Int, private var content: String?) : ILeave {


    override fun getName(): String {

        return name
    }

    override fun getNum(): Int = age

    override fun getContent(): String {
        return content?: "ss"

    }
}