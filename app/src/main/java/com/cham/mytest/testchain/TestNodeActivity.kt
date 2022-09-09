package com.cham.mytest.testchain

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.cham.mytest.R
import com.cham.mytest.databinding.ActivityTestNodeBinding
import com.cham.mytest.ui.main.PageViewModel

class TestNodeActivity : AppCompatActivity() {


    companion object {

        val TAG = "TestNodeActivity"

    }

    private lateinit var mBinding: ActivityTestNodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        mBinding = ActivityTestNodeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_test_node)

        //进去就是折叠的
        mBinding.appbar.setExpanded(false)
        this.resources
        //二叉树简单构建
        val nodes = mutableListOf<Node>()
        for (i in 0..7) {
            nodes.add(Node(i))
        }
        nodes[0].left = nodes[1]
        nodes[0].right = nodes[2]

        nodes[1].left = nodes[3]

        nodes[2].left = nodes[4]
        nodes[2].right = nodes[5]

        nodes[3].left = nodes[6]
        nodes[3].right = nodes[7]

        //责任链
        val leave: ILeave = Leave("小花", 5, "身体不适")

        val iHandler1 = 组长()
        iHandler1.handlerLeave(leave)

        val iHandler2 = 主管()
        iHandler2.handlerLeave(leave)

        val iHandler3 = 领导()
        iHandler3.handlerLeave(leave)

        mBinding.ivPorsche.setOnClickListener {
            nodes[0].dfs()
        }
        mBinding.ivManman.setOnClickListener {

            /**
             * (1)在manifest配置文件中配置了scheme参数
             * (2)网络端获取url
             * (3)跳转
             */
            val url = "ooo://www.2beauti.com/wx/index.html?tmn=1"

            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
                )
            )
            //   startActivity(Intent(this,TestAtoActivity::class.java))

        }
    }


}