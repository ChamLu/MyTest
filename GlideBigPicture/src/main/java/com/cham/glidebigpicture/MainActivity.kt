package com.cham.glidebigpicture

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cham.app.GlideApp
import com.cham.glidebigpicture.databinding.ActivityStartBinding
import com.cham.service.后台服务
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.w3c.dom.Node
import java.lang.ref.WeakReference
import kotlin.reflect.KProperty


/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/3/13 14:05
 * @UpdateUser:
 * @UpdateDate:     2021/3/13 14:05
 * @UpdateRemark:
 */
class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    private val mBinding: ActivityStartBinding by lazy {
        ActivityStartBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        onClick()

        val s = listOf(1, 2, 3, 4, 5, 6, 7, 8)
        println(s.filter(::isOdd))

        val oddLength = compose(::length, ::isOdd)
        val strings = listOf("a", "ab", "abc")
        println(strings.filter(oddLength))

    }

    /**
     * 高阶函数  我都懵逼
     * */
    fun <A, B, C> compose(g: (A) -> B, f: (B) -> C): (A) -> C {
        return { x -> f(g(x)) }
    }

    fun isOdd(x: Int) = x % 2 != 0

    fun length(s: String) = s.length


    private fun onClick() {
        mBinding.btnStart.setOnClickListener {
            Glide.with(this).load(R.drawable.qmsht)
               .override(640, 11888)
                .into(mBinding.ivBig)

            val s = GlideApp
                .with(this)
                .load(R.drawable.qmsht)

                .override(640, 11888)
                .into(mBinding.ivBig)


        }

        mBinding.ivBig.setOnClickListener {
            ExpandableTextActivity.startExpandableTextActivity(this)
            val mServiceIntent = Intent(this, 后台服务::class.java)
            startService(mServiceIntent)

        }
        val alertDialog = MaterialAlertDialogBuilder(this)
        alertDialog.setMessage("去开启无障碍服务哦")
            .setTitle("无障碍服务")
            .setNegativeButton("Here we go") { dialog, which ->

                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
            .setPositiveButton("我再想想") { dialog, which ->

            }

        mBinding.btnAs.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }

        mBinding.btnAs22.setOnClickListener {
            openApp("com.netease.cloudmusic")
        }

        Log.e(TAG, "onClick: " + getString("004"))
        val node = Node("aaa", "bbb")
        val s222 = WeakReferenceNode(node)
        mBinding.btnNodeGet.setOnClickListener {

            //弱引用
            s222.get()?.invoke()

            //   node.invoke()
        }

        putString("001", "aCham")
        putString("002", "bCham")
        putString("003", "cCham")
        putString("004", "dCham")
        putString("005", "eCham")
        putString("006", "fCham")
        putString("007", "gCham")

        mBinding.btnNodePut.setOnClickListener {
            putString("008", "gCham")

        }


        //拓展
        val s: MutableList<String> = mutableListOf()
        s.addBy("2")

        //委托
        var d: Int by DelegateTest()
        d = 6

        Log.e(TAG, "onClick: $d")


    }


    private fun openApp(packageName: String): Boolean {
        val packageManager = this.packageManager
        //打开网易云
        //  val packageName = "com.netease.cloudmusic"
        val pi: PackageInfo? = try {
            packageManager.getPackageInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("className", "" + e.message)
            return false
        }
        val resolveIntent = Intent(Intent.ACTION_MAIN, null)
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        resolveIntent.setPackage(pi!!.packageName)
        val apps =
            packageManager.queryIntentActivities(resolveIntent, 0)
        val resolveInfo = apps.iterator().next()

        if (resolveInfo != null) {
            val className = resolveInfo.activityInfo.name
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val cn = ComponentName(packageName, className)
            intent.component = cn
            startActivity(intent)
        } else {
            return false
        }

        return true
    }

    /**
     * 拓展函数
     * */
    fun <T> MutableList<T>.addBy(t: T): MutableList<T> {
        this.add(t)
        return this
    }

    /**
     * 扩展属性
     * */
    var <T> MutableList<T>.lastData: T
        get() {
            return this[size - 1]
        }
        set(value) {
            this[size - 1] = value
        }

    /**
     * 属性委托示例
     * */
    inner class DelegateTest<T>() {

        operator fun getValue(thisRef: T?, property: KProperty<*>): Int {
            return 1
        }

        operator fun setValue(thisRef: T?, property: KProperty<*>, value: T?) {
            Log.e(TAG, "setValue: $value  $thisRef ${property.name}")
        }

    }

    /**-----------------------LRU----------------------------------------------*/

    private var head: Node? = null
    private var end: Node? = null

    private var limit: Int = 5
    private var hashMap = hashMapOf<String, Node>()

    private fun getString(key: String): String? {
//        val node: Node? = hashMap[key]
//        node?.let {
//            refreshNode(it)
//            return it.value
//        }?: return ""

        hashMap[key]?.let {
            refreshNode(it)
            return it.value
        } ?: return null

    }

    private fun putString(key: String, value: String) {
        var node: Node? = hashMap[key]

        if (node == null) {
            //如果key不存在，插入key-value
            if (hashMap.size >= limit) {
                //节点删除
                val oldKey = removeNode(head!!)
                //哈希表删除
                hashMap.remove(oldKey)
            }
            node = Node(key, value)

            addNode(node)

            hashMap[key] = node

        } else {

            node.value = value

            refreshNode(node)

        }
    }

    /**
     * 刷新被访问的节点位置
     * */
    private fun refreshNode(node: Node?) {

        node?.let {
            //如果是访问尾节点的话 ，不需要移动 反之

            if (it != end) {
                //移除节点

                removeNode(it)

                addNode(it)
            }
        }

    }

    /**
     * 移除节点
     * */
    private fun removeNode(node: Node): String {

        when (node) {
            end -> {
                end = end!!.pre
            }
            head -> {
                head = head!!.next
            }
            else -> {
                //移除中间节点
                node.pre!!.next = node.next
                node.next!!.pre = node.pre
            }
        }
        return node.key;

    }

    /**
     * 尾部重新加入节点
     * */
    private fun addNode(node: Node) {

        if (end != null) {
            end!!.next = node
            node.pre = end
            node.next = null
        }

        end = node

        if (head == null) {
            head = node
        }
    }


    inner class Node(var key: String, var value: String) {
        var pre: Node? = null
        var next: Node? = null

        operator fun invoke(): String {


            return "$key : $value"
        }

        operator fun component1(): String = key

        operator fun component2(): String = value

        operator fun component3(): Int = 0


    }


    /**
     * 弱引用
     * 当一个对象仅仅被weak reference（弱引用）指向,
     * 而没有任何其他strong reference（强引用）指向的时候,
     * 如果这时GC运行, 那么这个对象就会被回收，不论当前的内存空间是否足够，
     * 这个对象都会被回收!!!
     *
     *
     * 继承WeakReference，将Node作为弱引用。
     * 注意到时候回收的是Node，而不是WeakReferenceNode
     * */
    inner class WeakReferenceNode(referent: Node) : WeakReference<Node>(referent)


}