package com.cham.lib_processor

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import javax.lang.model.element.*

/**
 * 注解内容存储实例，一个文件里的内容都存储在这个里面
 * 也可以说同一个TypeElement(类)内容都存在这里
 * */
class InjectInfo(val element: TypeElement) {


    //类名
    val className = element.asClassName()
    val viewClass = ClassName("android.view","View")
    //包名
    val packageName = getPackageName(element).qualifiedName.toString()

    //布局Id
    var layoutId = -1
    //View 注解数据可能有多个 注意是 VariableElement
    val viewMap = hashMapOf<Int,VariableElement>()


    private  fun getPackageName(element:Element):PackageElement{
        var e= element
        while (e.kind!=ElementKind.PACKAGE){
            e= e.enclosingElement
        }
        return e as PackageElement
    }


     fun getClassName(element: Element):ClassName= element.asType().asTypeName() as ClassName

     fun generateConstructor(): FunSpec {
        val builder =FunSpec.constructorBuilder().addParameter("target",className)
            .addParameter("view",viewClass)

        if(layoutId!=-1){

            builder.addStatement("target.setContentView(%L)",layoutId)
        }

        viewMap.forEach { (id,variableElement)->
            builder.addStatement("target.%N =view.findViewById(%L)",
            variableElement.simpleName,id)

        }

        return builder.build()
    }
}